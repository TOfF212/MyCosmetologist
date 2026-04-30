package com.hfad.mycosmetologist.data.repository

import com.google.firebase.auth.actionCodeSettings
import com.hfad.mycosmetologist.data.source.local.auth.AuthLocalStorage
import com.hfad.mycosmetologist.data.source.remote.auth.FirebaseAuthSource
import com.hfad.mycosmetologist.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val firebase: FirebaseAuthSource,
    private val local: AuthLocalStorage
): AuthRepository {
    override suspend fun sendMagicLink(email: String) {
        val settings = actionCodeSettings {
            url = "https://beules.page.link/login"
            handleCodeInApp = true
            setAndroidPackageName("com.hfad.mycosmetologist", true, null)
        }

        firebase.sendMagicLink(email, settings)
        local.saveEmail(email)
    }

    override suspend fun signInWithEmailLink(email: String, emailLink: String) {
        firebase.signIn(email, emailLink)
    }
}

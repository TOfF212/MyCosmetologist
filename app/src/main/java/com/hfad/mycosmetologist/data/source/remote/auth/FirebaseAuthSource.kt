package com.hfad.mycosmetologist.data.source.remote.auth

import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthSource(
    private val auth: FirebaseAuth,
) {

    suspend fun sendMagicLink(email: String, settings: ActionCodeSettings) {
        auth.sendSignInLinkToEmail(email, settings).await()
    }

    suspend fun signIn(email: String, emailLink: String) {
        auth.signInWithEmailLink(email, emailLink).await()
    }

    fun observeAuth(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener {
            trySend(it.currentUser)
        }

        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    fun isSignInLink(link: String): Boolean =
        auth.isSignInWithEmailLink(link)

    fun signOut() = auth.signOut()

}

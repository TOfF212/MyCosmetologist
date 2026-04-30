package com.hfad.mycosmetologist.domain.repository

interface AuthRepository {

    suspend fun sendMagicLink(email: String)
    suspend fun signInWithEmailLink(email: String, emailLink: String)

}

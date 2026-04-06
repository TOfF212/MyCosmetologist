package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.session.SessionState
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun observeSession(): Flow<SessionState>

    suspend fun signIn(workerId: String)

    suspend fun signOut()
}

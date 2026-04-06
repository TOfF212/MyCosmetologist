package com.hfad.mycosmetologist.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hfad.mycosmetologist.domain.repository.SessionRepository
import com.hfad.mycosmetologist.domain.session.SessionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSessionRepository
@Inject
constructor(
    private val preferencesDataStore: DataStore<Preferences>,
) : SessionRepository {
    override fun observeSession(): Flow<SessionState> =
        preferencesDataStore.data.map { preferences ->
            val workerId = preferences[AUTHORIZED_WORKER_ID_KEY]
            if (workerId.isNullOrBlank()) {
                SessionState.Unauthorized
            } else {
                SessionState.Authorized(workerId)
            }
        }

    override suspend fun signIn(workerId: String) {
        preferencesDataStore.edit { preferences ->
            preferences[AUTHORIZED_WORKER_ID_KEY] = workerId
        }
    }

    override suspend fun signOut() {
        preferencesDataStore.edit { preferences ->
            preferences.remove(AUTHORIZED_WORKER_ID_KEY)
        }
    }

    private companion object {
        val AUTHORIZED_WORKER_ID_KEY = stringPreferencesKey("authorized_worker_id")
    }
}

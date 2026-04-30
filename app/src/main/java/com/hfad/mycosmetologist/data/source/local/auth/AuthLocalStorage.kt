package com.hfad.mycosmetologist.data.source.local.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthLocalStorage(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("auth_email")
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    fun getEmail(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[EMAIL_KEY]
        }
    }
}

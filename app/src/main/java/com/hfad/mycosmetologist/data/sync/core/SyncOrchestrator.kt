package com.hfad.mycosmetologist.data.sync.core

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SyncOrchestrator
@Inject constructor(
    private val syncables: Set<@JvmSuppressWildcards Syncable>
) {
    suspend fun syncAll(){
        syncables.forEach { syncable ->
            runCatching { syncable.sync() }
        }
    }
}

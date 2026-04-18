package com.hfad.mycosmetologist.data.sync.client

import com.hfad.mycosmetologist.data.mapper.toRemoteEntity
import com.hfad.mycosmetologist.data.source.local.db.dao.ClientDao
import com.hfad.mycosmetologist.data.source.remote.client.ClientFirestoreDataSource
import com.hfad.mycosmetologist.data.sync.core.Syncable
import javax.inject.Inject

class ClientSyncManager @Inject constructor(
    private val dao: ClientDao,
    private val remote: ClientFirestoreDataSource,
) : Syncable {

    override suspend fun sync() {
        val unsynced = dao.getUnsynced()

        unsynced.forEach { client ->
            try {
                remote.saveClient(client.toRemoteEntity())
                dao.insert(client.copy(isSynced = true))
            } catch (_: Exception) {
            }
        }
    }
}

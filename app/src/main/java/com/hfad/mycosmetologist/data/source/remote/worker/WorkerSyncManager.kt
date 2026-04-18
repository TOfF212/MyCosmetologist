package com.hfad.mycosmetologist.data.sync.workers

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.mapper.toRemoteEntity
import com.hfad.mycosmetologist.data.source.local.db.dao.WorkerDao
import com.hfad.mycosmetologist.data.source.remote.worker.WorkerFirestoreDataSource
import com.hfad.mycosmetologist.data.sync.core.Syncable
import javax.inject.Inject

class WorkerSyncManager @Inject constructor(
    private val dao: WorkerDao,
    private val remote: WorkerFirestoreDataSource,
) : Syncable {

    suspend fun sync() {
        pushLocalChanges()
    }

    private suspend fun pushLocalChanges() {
        val unsynced = dao.getUnsynced()

        unsynced.forEach { worker ->
            try {
                remote.saveWorker(worker.toRemoteEntity())
                dao.insert(worker.copy(isSynced = true))
            } catch (e: Exception) {
            }
        }
    }


}

package com.hfad.mycosmetologist.data.sync.service

import com.hfad.mycosmetologist.data.mapper.toRemoteEntity
import com.hfad.mycosmetologist.data.source.local.db.dao.ServiceDao
import com.hfad.mycosmetologist.data.source.remote.service.ServiceFirestoreDataSource
import com.hfad.mycosmetologist.data.sync.core.Syncable
import javax.inject.Inject

class ServiceSyncManager @Inject constructor(
    private val dao: ServiceDao,
    private val remote: ServiceFirestoreDataSource,
) : Syncable {


        override suspend fun sync() {
            pushLocalChanges()
        }

        private suspend fun pushLocalChanges() {
            val unsynced = dao.getUnsynced()

            unsynced.forEach { service ->
                try {
                    remote.saveService(service.toRemoteEntity())
                    dao.update(service.copy(isSynced = true))
                } catch (e: Exception) {
                }
            }
        }


    }

package com.hfad.mycosmetologist.data.sync.appointmentservices

import com.hfad.mycosmetologist.data.mapper.toRemoteEntity
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.remote.appointmentService.AppointmentServiceFirestoreDataSource
import com.hfad.mycosmetologist.data.sync.core.Syncable
import javax.inject.Inject

class AppointmentServiceSyncManager @Inject constructor(
    private val dao: AppointmentServiceDao,
    private val remote: AppointmentServiceFirestoreDataSource,
) : Syncable {

    override suspend fun sync() {
        val unsynced = dao.getUnsynced()

        unsynced.forEach { appointmentService ->
            try {
                remote.saveAppointmentService(appointmentService.toRemoteEntity())
                dao.update(appointmentService.copy(isSynced = true))
            } catch (_: Exception) {
            }
        }
    }
}

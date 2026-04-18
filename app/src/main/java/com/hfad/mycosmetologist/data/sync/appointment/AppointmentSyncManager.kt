package com.hfad.mycosmetologist.data.sync.appointments

import com.hfad.mycosmetologist.data.mapper.toRemoteEntity
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentDao
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.remote.appointment.AppointmentFirestoreDataSource
import com.hfad.mycosmetologist.data.sync.core.Syncable
import javax.inject.Inject

class AppointmentSyncManager @Inject constructor(
    private val dao: AppointmentDao,
    private val appointmentServiceDao: AppointmentServiceDao,
    private val remote: AppointmentFirestoreDataSource,
) : Syncable {

    override suspend fun sync() {
        val unsynced = dao.getUnsynced()

        unsynced.forEach { appointment ->
            try {
                val servicesIds = appointmentServiceDao.getByAppointmentId(appointment.id).map { it.serviceId }
                remote.saveAppointment(appointment.toRemoteEntity(servicesIds))
                dao.insert(appointment.copy(isSynced = true))
            } catch (_: Exception) {
            }
        }
    }
}

package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.AppDatabase
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentDao
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentServiceDbEntity
import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import java.time.LocalDateTime
import java.time.ZoneOffset


class AppointmentRepositoryImpl(
    private val appointmentDao: AppointmentDao,
    private val appointmentServiceDao: AppointmentServiceDao
): AppointmentRepository {

    override suspend fun createAppointment(appointment: Appointment){
        appointmentDao.insert(appointment.toDbModel())
        appointment.servicesIds.forEach {
            appointmentServiceDao.insert(
                AppointmentServiceDbEntity(
                    appointment.id,
                    it
                )
            )
        }
    }

    override suspend fun updateAppointment(appointment: Appointment){
        appointmentServiceDao.deleteByAppointmentId(appointment.id)
        appointmentDao.update(appointment.toDbModel())
        appointment.servicesIds.forEach {
            appointmentServiceDao.insert(
                AppointmentServiceDbEntity(
                    appointment.id,
                    it
                )
            )
        }
    }


    override suspend fun getAppointmentById(workerId: String, id: String): Appointment?{

        val base = appointmentDao.getById(workerId, id)?.toDomainModel()
        val servicesIds = mutableListOf<String>()

        appointmentServiceDao.getByAppointmentId(id).forEach {
            servicesIds.add(it.serviceId)
        }

        val result = base?.copy(
           servicesIds =  servicesIds
        )
        return result
    }

    override suspend fun getAppointmentsByDate(workerId: String, date: LocalDateTime): List<Appointment>{
        val result = mutableListOf<Appointment>()
        val daysFromEpoch = date.atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli() / 86400000
        appointmentDao.getByDate(
            workerId,
            daysFromEpoch * 86400000,
            (daysFromEpoch + 1) * 86400000)
            .forEach {
                val servicesIds = mutableListOf<String>()

                appointmentServiceDao.getByAppointmentId(it.id).forEach {
                    servicesIds.add(it.serviceId)
                }

                result.add(
                    it.toDomainModel().copy(
                    servicesIds = servicesIds // N+1
                ))
            }
        return result.toList()
    }

    override suspend fun getPastAppointments(workerId: String, date: LocalDateTime): List<Appointment>{
        val result = mutableListOf<Appointment>()
        val milliFromEpoch = date.atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        appointmentDao.get20Last(
            workerId,
            milliFromEpoch)
            .forEach {
                val servicesIds = mutableListOf<String>()
                appointmentServiceDao.getByAppointmentId(it.id).forEach {
                    servicesIds.add(it.serviceId)
                }

                result.add(
                    it.toDomainModel().copy(
                        servicesIds = servicesIds
                    ))
            }
        return result.toList()
    }

    override suspend fun isTimeBusy(appointment: Appointment): Boolean{

        val startTimeMilli = appointment.startTime
            .atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()

        val previous = appointmentDao.getPrevious(
            appointment.workerId,
            startTimeMilli
        )

        val next = appointmentDao.getNext(
            appointment.workerId,
            startTimeMilli
        )

        return (previous?.endTime?.isAfter(appointment.startTime) == true) ||
                (next?.startTime?.isBefore(appointment.endTime) == true)
    }

}
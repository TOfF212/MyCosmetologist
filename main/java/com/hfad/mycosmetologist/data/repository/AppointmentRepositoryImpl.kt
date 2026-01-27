package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.AppDatabase
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentDao
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentServiceDbEntity
import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.hfad.mycosmetologist.domain.util.Result
import kotlinx.coroutines.flow.catch

class AppointmentRepositoryImpl @Inject constructor(
    private val appointmentDao: AppointmentDao,
    private val appointmentServiceDao: AppointmentServiceDao
): AppointmentRepository {

    override suspend fun createAppointment(appointment: Appointment): Flow<Result<Unit>> = flow{
        emit(Result.Loading)

        try{
            appointmentServiceDao.deleteByAppointmentId(appointment.workerId)

            appointmentDao.insert(appointment.toDbModel())

            appointment.servicesIds.forEach {
                appointmentServiceDao.insert(
                    AppointmentServiceDbEntity(
                        appointment.id,
                        it
                    )
                )
            }
            emit(Result.Success(Unit))
        } catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }

    override suspend fun updateAppointment(appointment: Appointment): Flow<Result<Unit>> = flow{
        emit(Result.Loading)

        try{
            appointmentServiceDao.deleteByAppointmentId(appointment.workerId)

            appointmentDao.update(appointment.toDbModel())

            appointment.servicesIds.forEach {
                appointmentServiceDao.insert(
                    AppointmentServiceDbEntity(
                        appointment.id,
                        it
                    )
                )
            }

            emit(Result.Success(Unit))

        } catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }


    override suspend fun getAppointmentById(workerId: String, id: String): Flow<Result<Appointment>> = flow{
        emit(Result.Loading)
         try{
             val base = appointmentDao.getById(workerId, id)?.toDomainModel()
             val servicesIds = mutableListOf<String>()

             appointmentServiceDao.getByAppointmentId(id).forEach {
                 servicesIds.add(it.serviceId)
             }

             val result = base?.copy(
                 servicesIds =  servicesIds
             )
             emit(Result.Success(result!!))
         } catch (e: Exception){
             emit(Result.Error(e))
         }

    }.catch{ e->
        emit(Result.Error(e))
    }

    override suspend fun getAppointmentsByDate(workerId: String, date: LocalDateTime): Flow<Result<List<Appointment>>> = flow{

        emit(Result.Loading)
        try {
            val result = mutableListOf<Appointment>()
            val daysFromEpoch = date.atZone(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli() / 86400000
            appointmentDao.getByDate(
                workerId,
                daysFromEpoch * 86400000,
                (daysFromEpoch + 1) * 86400000
            )
                .forEach {
                    val servicesIds = mutableListOf<String>()

                    appointmentServiceDao.getByAppointmentId(it.id).forEach {
                        servicesIds.add(it.serviceId)
                    }

                    result.add(
                        it.toDomainModel().copy(
                            servicesIds = servicesIds // N+1
                        )
                    )
                }
            emit(Result.Success(result.toList()))
        } catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }

    override suspend fun getPastAppointments(workerId: String, date: LocalDateTime): Flow<Result<List<Appointment>>> = flow{
        emit(Result.Loading)
        try{
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
            emit(Result.Success(result.toList()))
        } catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }

    override suspend fun isTimeBusy(appointment: Appointment): Flow<Result<Boolean>> = flow{

        emit(Result.Loading)

        try{
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

            emit(Result.Success( (previous?.endTime?.isAfter(appointment.startTime) == true) ||
                    (next?.startTime?.isBefore(appointment.endTime) == true) ))
        }catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }

}
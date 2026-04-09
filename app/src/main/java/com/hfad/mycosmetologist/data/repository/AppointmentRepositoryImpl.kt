package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentDao
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentServiceDbEntity
import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import com.hfad.mycosmetologist.domain.util.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.Instant
import java.time.temporal.ChronoUnit

class AppointmentRepositoryImpl
@Inject
constructor(
    private val appointmentDao: AppointmentDao,
    private val appointmentServiceDao: AppointmentServiceDao,
) : AppointmentRepository {
    override fun createAppointment(appointment: Appointment): Flow<Result<Unit>> =
        flow {
            emit(Result.Loading)

            try {

                appointmentDao.insert(appointment.toDbModel())

                appointment.servicesIds.forEach { serviceId ->
                    appointmentServiceDao.insert(
                        AppointmentServiceDbEntity(
                            appointmentId = appointment.id,
                            serviceId =  serviceId,
                        ),
                    )
                }

                emit(Result.Success(Unit))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { emit(Result.Error(it)) }

    override fun updateAppointment(appointment: Appointment): Flow<Result<Unit>> =
        flow {
            emit(Result.Loading)

            try {
                appointmentServiceDao.deleteByAppointmentId(appointment.id)

                appointmentDao.update(appointment.toDbModel())

                appointment.servicesIds.forEach { serviceId ->
                    appointmentServiceDao.insert(
                        AppointmentServiceDbEntity(
                            appointmentId = appointment.id,
                            serviceId =  serviceId,
                        ),
                    )
                }

                emit(Result.Success(Unit))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { emit(Result.Error(it)) }

    override fun getAppointmentById(
        workerId: String,
        id: String,
    ): Flow<Result<Appointment>> =
        flow {
            emit(Result.Loading)

            try {
                val base =
                    appointmentDao
                        .getById(workerId, id)
                        ?.toDomainModel()
                        ?: throw IllegalStateException("Appointment not found: $id")

                val servicesIds =
                    appointmentServiceDao
                        .getByAppointmentId(id)
                        .map { it.serviceId }

                emit(
                    Result.Success(
                        base.copy(servicesIds = servicesIds.toMutableList()),
                    ),
                )
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { emit(Result.Error(it)) }

    override fun getAppointmentsByDate(
        workerId: String,
        startOfDay: Instant,
    ): Flow<Result<List<Appointment>>> =
        flow {
            emit(Result.Loading)

            try {
                val endOfDay: Instant =
                    startOfDay.plus(1, ChronoUnit.DAYS)

                val result =
                    appointmentDao
                        .getByDate(
                            workerId,
                            startOfDay.toEpochMilli(),
                            endOfDay.toEpochMilli(),
                        ).map { entity ->
                            val servicesIds =
                                appointmentServiceDao
                                    .getByAppointmentId(entity.id)
                                    .map { it.serviceId }

                            entity
                                .toDomainModel()
                                .copy(servicesIds = servicesIds.toMutableList())
                        }

                emit(Result.Success(result))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { emit(Result.Error(it)) }

    override fun getPastAppointments(
        workerId: String,
        before: Instant,
    ): Flow<Result<List<Appointment>>> =
        flow {
            emit(Result.Loading)

            try {
                val result =
                    appointmentDao
                        .get20Last(
                            workerId,
                            before.toEpochMilli(),
                        ).map { entity ->
                            val servicesIds =
                                appointmentServiceDao
                                    .getByAppointmentId(entity.id)
                                    .map { it.serviceId }

                            entity
                                .toDomainModel()
                                .copy(servicesIds = servicesIds.toMutableList())
                        }

                emit(Result.Success(result))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { emit(Result.Error(it)) }

    override fun getAppointmentsByClient(
        workerId: String,
        clientId: String,
    ): Flow<Result<List<Appointment>>> =
        flow {
            emit(Result.Loading)

            try {
                val result =
                    appointmentDao
                        .getByClientId(
                            workerId,
                            clientId,
                        ).map { entity ->
                            val servicesIds =
                                appointmentServiceDao
                                    .getByAppointmentId(entity.id)
                                    .map { it.serviceId }

                            entity
                                .toDomainModel()
                                .copy(servicesIds = servicesIds.toMutableList())
                        }

                emit(Result.Success(result))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { emit(Result.Error(it)) }

    override fun isTimeBusy(appointment: Appointment): Flow<Result<Boolean>> =
        flow {
            emit(Result.Loading)

            try {
                val startMillis = appointment.startTime.toEpochMilli()

                var previous =
                    appointmentDao.getPrevious(
                        appointment.workerId,
                        startMillis,
                    )
                while (previous?.cancelled ?: false){
                    previous = appointmentDao.getNext(
                        previous.id,
                        previous.startTime.toEpochMilli()
                    )
                }
                var next =
                    appointmentDao.getNext(
                        appointment.workerId,
                        startMillis,
                    )

                while (next?.cancelled ?: false){
                    next = appointmentDao.getNext(
                        next.id,
                        next.startTime.toEpochMilli()
                    )
                }

                val busy =
                    (previous?.endTime?.isAfter(appointment.startTime) ?: false)
                        || (next?.startTime?.isBefore(appointment.endTime) ?: false)

                emit(Result.Success(busy))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { emit(Result.Error(it)) }
}

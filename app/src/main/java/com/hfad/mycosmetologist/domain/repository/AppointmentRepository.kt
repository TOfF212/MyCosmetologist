package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface AppointmentRepository {
    fun createAppointment(appointment: Appointment): Flow<Result<Unit>>
    fun getUpcomingAppointments(
        workerId: String,
        from: Instant,
    ): Flow<Result<List<Appointment>>>


    fun updateAppointment(appointment: Appointment): Flow<Result<Unit>>

    fun getAppointmentById(
        workerId: String,
        id: String,
    ): Flow<Result<Appointment>>

    fun getAppointmentsByDate(
        workerId: String,
        startOfDay: Instant,
    ): Flow<Result<List<Appointment>>>

    fun getPastAppointments(
        workerId: String,
        before: Instant,
    ): Flow<Result<List<Appointment>>>

    fun getAppointmentsByClient(
        workerId: String,
        clientId: String
    ): Flow<Result<List<Appointment>>>

    fun isTimeBusy(appointment: Appointment): Flow<Result<Boolean>>
}

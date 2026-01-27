package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.domain.entity.Appointment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

interface AppointmentRepository {

    suspend fun createAppointment(appointment: Appointment): Flow<Result<Unit>>


    suspend fun updateAppointment(appointment: Appointment): Flow<Result<Unit>>

    suspend fun getAppointmentById(workerId: String, id: String): Flow<Result<Appointment>>

    suspend fun getAppointmentsByDate(workerId: String, date: LocalDateTime): Flow<Result<List<Appointment>>>

    suspend fun getPastAppointments(workerId: String, date: LocalDateTime): Flow<Result<List<Appointment>>>

    suspend fun isTimeBusy(appointment: Appointment): Flow<Result<Boolean>>
}
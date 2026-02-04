package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.domain.entity.Appointment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Instant

interface AppointmentRepository {

     fun createAppointment(appointment: Appointment): Flow<Result<Unit>>


     fun updateAppointment(appointment: Appointment): Flow<Result<Unit>>

     fun getAppointmentById(workerId: String, id: String): Flow<Result<Appointment>>

     fun getAppointmentsByDate(workerId: String, startOfDay: Instant): Flow<Result<List<Appointment>>>

     fun getPastAppointments(workerId: String, before: Instant): Flow<Result<List<Appointment>>>

     fun isTimeBusy(appointment: Appointment): Flow<Result<Boolean>>
}
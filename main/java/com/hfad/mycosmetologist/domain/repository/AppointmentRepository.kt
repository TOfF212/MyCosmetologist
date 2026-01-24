package com.hfad.mycosmetologist.domain.repository


import com.hfad.mycosmetologist.domain.entity.Appointment
import java.time.LocalDate
import java.time.LocalDateTime

interface AppointmentRepository {

    suspend fun createAppointment(appointment: Appointment)

    suspend fun updateAppointment(appointment: Appointment)

    suspend fun getAppointmentById(workerId: Long, id: Long): Appointment?

    suspend fun getAppointmentsByDate(workerId: Long, date: LocalDateTime): List<Appointment>

    suspend fun getPastAppointments(workerId: Long, date: LocalDateTime): List<Appointment>

    suspend fun isTimeBusy(appointment: Appointment): Boolean
}
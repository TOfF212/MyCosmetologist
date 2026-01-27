package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import java.time.LocalDate
import java.time.LocalDateTime

class GetAppointmentsByDate @Inject constructor (private val repository: AppointmentRepository) {

    suspend operator fun invoke(workerId: String,date: LocalDateTime) = repository.getAppointmentsByDate(workerId, date)

}
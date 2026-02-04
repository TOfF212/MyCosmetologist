package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

class GetPastAppointments @Inject constructor (private val repository: AppointmentRepository) {

     operator fun invoke(workerId: String, date: Instant) =
       repository.getPastAppointments(workerId,date)

}
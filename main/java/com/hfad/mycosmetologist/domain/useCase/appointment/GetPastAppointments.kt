package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import java.time.LocalDate
import java.time.LocalDateTime

class GetPastAppointments (private val repository: AppointmentRepository) {

    suspend operator fun invoke(workerId: String, date: LocalDateTime): List<Appointment>{
        return repository.getPastAppointments(workerId,date)
    }
}
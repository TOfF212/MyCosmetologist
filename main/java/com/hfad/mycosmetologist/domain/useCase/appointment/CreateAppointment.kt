package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository

class CreateAppointment (private val repository: AppointmentRepository) {

    suspend operator fun invoke(appointment: Appointment): CreateAppointmentResult {

        if (repository.isTimeBusy(appointment)) return CreateAppointmentResult.InvalidTime

        repository.createAppointment(appointment)
        return CreateAppointmentResult.Success
    }
}
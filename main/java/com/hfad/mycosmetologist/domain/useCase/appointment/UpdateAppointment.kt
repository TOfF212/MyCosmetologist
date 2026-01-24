package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import com.hfad.mycosmetologist.domain.usecases.appointment.UpdateAppointmentResult

class UpdateAppointment(private val repository: AppointmentRepository) {
    suspend operator fun invoke(appointment: Appointment): UpdateAppointmentResult{
        if (repository.isTimeBusy(appointment)) return UpdateAppointmentResult.InvalidTime

        repository.updateAppointment(appointment)
        return UpdateAppointmentResult.Success
    }
}
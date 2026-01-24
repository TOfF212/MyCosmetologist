package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository

class GetAppointment(
    private val repository: AppointmentRepository
) {

    suspend operator fun invoke(workerId: String,appointmentId: String): Appointment? {
        return repository.getAppointmentById(workerId,appointmentId)
    }
}
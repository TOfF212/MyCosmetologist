package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository

class GetAppointment(
    private val repository: AppointmentRepository
) {

    suspend operator fun invoke(workerId: Long,appointmentId: Long): Appointment? {
        return repository.getAppointmentById(workerId,appointmentId)
    }
}
package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import jakarta.inject.Inject

class GetAppointmentById
@Inject
constructor(
    private val repository: AppointmentRepository,
) {
    operator fun invoke(
        workerId: String,
        appointmentId: String,
    ) = repository.getAppointmentById(workerId, appointmentId)
}

package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import jakarta.inject.Inject

class GetAppointmentsByClient @Inject
constructor(
    private val repository: AppointmentRepository,
) {
    operator fun invoke(
        workerId: String,
        clientId: String,
    ) = repository.getAppointmentsByClient(workerId, clientId)
}

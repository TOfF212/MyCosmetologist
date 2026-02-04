package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import java.time.Instant

class GetPastAppointments
    @Inject
    constructor(
        private val repository: AppointmentRepository,
    ) {
        operator fun invoke(
            workerId: String,
            date: Instant,
        ) = repository.getPastAppointments(workerId, date)
    }

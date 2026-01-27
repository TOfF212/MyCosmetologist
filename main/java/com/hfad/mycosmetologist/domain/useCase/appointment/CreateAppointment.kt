package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.exceptions.InvalidAppointmentTimeException
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import com.hfad.mycosmetologist.domain.util.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateAppointment @Inject constructor (private val repository: AppointmentRepository) {

    suspend operator fun invoke(appointment: Appointment) = repository.createAppointment(appointment) // TODO сделать обработку неправльного времени

}
package com.hfad.mycosmetologist.presentation.main.appointment.create.entity

import com.hfad.mycosmetologist.domain.entity.Service
import java.time.LocalDate
import java.time.LocalTime

sealed class CreateAppointmentAction {
    data class OnDateSelected(val date: LocalDate) : CreateAppointmentAction()
    data class OnStartTimeSelected(val time: LocalTime) : CreateAppointmentAction()
    data class OnEndTimeSelected(val time: LocalTime) : CreateAppointmentAction()
    data class OnServicesSelected(val services: List<Service>) : CreateAppointmentAction()
    data class OnAboutChanged(val text: String) : CreateAppointmentAction()
    object OnSaveClicked : CreateAppointmentAction()
}

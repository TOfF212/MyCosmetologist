package com.hfad.mycosmetologist.presentation.main.appointment.create.entity

import com.hfad.mycosmetologist.domain.entity.Service

sealed class CreateAppointmentAction {
    data class OnDateSelected(val date: Long?) : CreateAppointmentAction()
    data class OnStartTimeSelected(val hour: Int, val minutes: Int) : CreateAppointmentAction()
    data class OnEndTimeSelected(val hour: Int, val minutes: Int) : CreateAppointmentAction()
    data class OnServicesSelected(val service: Service) : CreateAppointmentAction()
    data class OnServicesDelete(val service: Service) : CreateAppointmentAction()

    data class OnAboutChanged(val text: String) : CreateAppointmentAction()
    object OnSaveClicked : CreateAppointmentAction()
}

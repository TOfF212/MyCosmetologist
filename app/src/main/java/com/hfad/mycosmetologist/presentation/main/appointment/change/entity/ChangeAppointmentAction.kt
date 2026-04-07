package com.hfad.mycosmetologist.presentation.main.appointment.change.entity

import com.hfad.mycosmetologist.domain.entity.Service

sealed class ChangeAppointmentAction {
    data class OnDateSelected(val date: Long?) : ChangeAppointmentAction()
    data class OnStartTimeSelected(val hour: Int, val minutes: Int) : ChangeAppointmentAction()
    data class OnEndTimeSelected(val hour: Int, val minutes: Int) : ChangeAppointmentAction()
    data class OnServicesSelected(val service: Service) : ChangeAppointmentAction()
    data class OnServicesDelete(val service: Service) : ChangeAppointmentAction()

    data class OnAboutChanged(val text: String) : ChangeAppointmentAction()
    object OnSaveClicked : ChangeAppointmentAction()
}

package com.hfad.mycosmetologist.presentation.main.appointment.change.entity

sealed class ChangeAppointmentEvent {
    data class Error(val message: String) : ChangeAppointmentEvent()
    object Navigate : ChangeAppointmentEvent()
    object OpenDatePicker : ChangeAppointmentEvent()
    object OpenStartTimePicker : ChangeAppointmentEvent()

    object OpenEndTimePicker : ChangeAppointmentEvent()
    object OpenServicesDialog : ChangeAppointmentEvent()
}

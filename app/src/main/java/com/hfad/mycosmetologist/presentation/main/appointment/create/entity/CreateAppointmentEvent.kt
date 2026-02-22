package com.hfad.mycosmetologist.presentation.main.appointment.create.entity

sealed class CreateAppointmentEvent {
    data class Error(val message: String) : CreateAppointmentEvent()
    object Navigate : CreateAppointmentEvent()
    object OpenDatePicker : CreateAppointmentEvent()
    object OpenStartTimePicker : CreateAppointmentEvent()

    object OpenEndTimePicker : CreateAppointmentEvent()
    object OpenServicesDialog : CreateAppointmentEvent()
}

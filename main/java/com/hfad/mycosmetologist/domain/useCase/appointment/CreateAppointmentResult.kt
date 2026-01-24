package com.hfad.mycosmetologist.domain.useCase.appointment

sealed class CreateAppointmentResult {
    object Success: CreateAppointmentResult()
    object InvalidTime: CreateAppointmentResult()
}
package com.hfad.mycosmetologist.domain.useCase.appointment

sealed class UpdateAppointmentResult {
        object Success: UpdateAppointmentResult()
        object InvalidTime: UpdateAppointmentResult()
}
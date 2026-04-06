package com.hfad.mycosmetologist.presentation.main.appointment.info.entity

import com.hfad.mycosmetologist.domain.entity.Service
import java.time.LocalDate

data class AppointmentInfoUiState(
    val isLoading: Boolean = true,
    val date: String,
    val time: String,
    val services: List<Service>,
    val clientName: String,
    val clientPhone: String
    ) {}

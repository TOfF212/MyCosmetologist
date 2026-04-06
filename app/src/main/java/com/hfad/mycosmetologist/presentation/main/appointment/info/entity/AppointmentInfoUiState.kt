package com.hfad.mycosmetologist.presentation.main.appointment.info.entity

import com.hfad.mycosmetologist.domain.entity.Service
import java.time.LocalDate

data class AppointmentInfoUiState(
    val isLoading: Boolean = true,
    val appointmentId: String = "",
    val clientId: String = "",
    val date: String = "",
    val time: String = "",
    val services: List<Pair<String, String>> = emptyList(),
    val clientName: String = "",
    val clientPhone: String = "",
    val comment: String = "",
    val status: String = "",
    val total: String = "",
)

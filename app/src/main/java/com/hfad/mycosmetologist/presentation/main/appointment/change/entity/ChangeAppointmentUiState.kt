package com.hfad.mycosmetologist.presentation.main.appointment.change.entity

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment
import java.time.LocalDate
import java.time.LocalTime
data class ChangeAppointmentUiState(
    val appointmentId: String = "",
    val clientId: String = "",
    val workerId: String = "",
    val selectedDate: LocalDate = LocalDate.now(),
    val clientName: String = "",
    val clientNumber: String = "",
    val appointmentList: List<PresentationAppointment> = emptyList(),
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val selectedServices: List<Service> = emptyList(),
    val about: String = "",
    val priceList: List<Service> = emptyList(),
    val isLoading: Boolean = true,
)

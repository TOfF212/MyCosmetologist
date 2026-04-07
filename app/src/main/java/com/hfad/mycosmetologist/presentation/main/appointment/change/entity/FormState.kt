package com.hfad.mycosmetologist.presentation.main.appointment.change.entity

import com.hfad.mycosmetologist.domain.entity.Service
import java.time.LocalDate
import java.time.LocalTime

data class FormState(
    val selectedDate: LocalDate = LocalDate.now(),
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val selectedServices: List<Service> = emptyList(),
    val about: String = ""
)

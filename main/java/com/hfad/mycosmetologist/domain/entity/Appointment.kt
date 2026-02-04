package com.hfad.mycosmetologist.domain.entity

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val workerId: String,
    val clientId: String,
    val servicesIds: MutableList<String>,
    val status: AppointmentStatus,
    val description: String,
    val startTime: Instant,
    val endTime: Instant

) {
}
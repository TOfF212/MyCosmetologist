package com.hfad.mycosmetologist.domain.entity

import java.time.Instant

data class Appointment(
    val id: String,
    val workerId: String,
    val clientId: String,
    val servicesIds: MutableList<String>,
    val status: AppointmentStatus,
    val description: String,
    val startTime: Instant,
    val endTime: Instant,
)

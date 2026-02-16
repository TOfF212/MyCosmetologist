package com.hfad.mycosmetologist.domain.entity

import java.time.Instant

data class Appointment(
    val id: String,
    val workerId: String,
    val clientId: String,
    val servicesIds: List<String>,
    val description: String,
    val startTime: Instant,
    val endTime: Instant,
    val cancelled: Boolean
)

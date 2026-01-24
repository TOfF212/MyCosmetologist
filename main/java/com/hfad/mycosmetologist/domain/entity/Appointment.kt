package com.hfad.mycosmetologist.domain.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class Appointment(
    val id: Long,
    val workerId: Long,
    val clientId: Long,
    val servicesIds: MutableList<Long>,
    val status: AppointmentStatus,
    val description: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime

) {
}
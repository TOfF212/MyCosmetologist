package com.hfad.mycosmetologist.domain.entities

data class Appointment(
    val id: Long,
    val workerId: Long,
    val clientId: Long,
    val servicesIds: MutableList<Long>,
    var durationMinutes: Int,
    val status: AppointmentStatus,
    val description: String
    ) {
}
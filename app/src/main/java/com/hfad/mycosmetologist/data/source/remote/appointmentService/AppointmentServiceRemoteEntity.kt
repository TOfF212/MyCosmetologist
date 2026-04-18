package com.hfad.mycosmetologist.data.source.remote.appointmentService

data class AppointmentServiceRemoteEntity(
    val id: String = "",
    val appointmentId: String = "",
    val serviceId: String = "",
    val updatedAt: Long = 0L,
    val isSynced: Boolean = false,
)

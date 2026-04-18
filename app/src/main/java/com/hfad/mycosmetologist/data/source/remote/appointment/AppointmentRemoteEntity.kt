package com.hfad.mycosmetologist.data.source.remote.appointment

data class AppointmentRemoteEntity(
    val id: String = "",
    val workerId: String = "",
    val clientId: String = "",
    val servicesIds: List<String> = emptyList(),
    val description: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val cancelled: Boolean = false,
    val updatedAt: Long = 0L,
)

package com.hfad.mycosmetologist.data.source.remote.worker

data class WorkerRemoteEntity (
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val about: String = "",
    val specialization: String = "",
    val experience: Int = 0,
    val email: String = "",
    val updatedAt: Long = 0L,
)

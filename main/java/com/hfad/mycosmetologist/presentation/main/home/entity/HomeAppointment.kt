package com.hfad.mycosmetologist.presentation.main.home.entity

data class HomeAppointment(
    val clientName: String,
    val startTime: String,
    val endTime: String,
    val services: String,
    val profit: String,
    val id: String
)

package com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity

import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment

data class ClientInfo(
    val id: String,
    val name: String,
    val phone: String,
    val about: String,
    val currentList: List<PresentationAppointment>,
    val pastList: List<PresentationAppointment>
)

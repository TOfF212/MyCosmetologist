package com.hfad.mycosmetologist.presentation.main.profile.entity

import com.hfad.mycosmetologist.domain.entity.Service

data class Profile (
    val priceList: List<Service>,
    val name: String,
    val phone: String,
    val about: String,
    val specialization: String,
    val experience: String,
    val email: String
)

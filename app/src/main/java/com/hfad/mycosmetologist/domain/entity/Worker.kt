package com.hfad.mycosmetologist.domain.entity

import androidx.room.ColumnInfo

data class Worker(
    val id: String,
    val name: String,
    val phone: String,
    val about: String,
    val password: String,
    val specialization: String,
    val experience: Int,
    val email: String,
)

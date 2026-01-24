package com.hfad.mycosmetologist.domain.entity

data class Client(
    val id: Long,
    val workerId: Long,
    val name: String,
    val phone: String,
    val about: String
) {
}
package com.hfad.mycosmetologist.domain.entity

data class Client(
    val id: String,
    val workerId: String,
    val name: String,
    val phone: String,
    val about: String
) {
}
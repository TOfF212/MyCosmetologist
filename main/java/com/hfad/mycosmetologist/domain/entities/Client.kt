package com.hfad.mycosmetologist.domain.entities

data class Client(
    val id: Long,
    val name: String,
    val phone: String,
    val about: String
) {
}
package com.hfad.mycosmetologist.domain.entities

data class Service(
    val id: Long,
    val name: String,
    val price: Int,
    val durationMinutes: Int,
    val description: String
){

}
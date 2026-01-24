package com.hfad.mycosmetologist.domain.entity

data class Service(
    val id: String,
    val workerId: String,
    val name: String,
    val price: Int,
    val durationMinutes: Int,
    val description: String
){

}
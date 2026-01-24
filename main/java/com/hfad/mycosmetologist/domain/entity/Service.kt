package com.hfad.mycosmetologist.domain.entity

data class Service(
    val id: Long,
    val workerId: Long,
    val name: String,
    val price: Int,
    val durationMinutes: Int,
    val description: String
){

}
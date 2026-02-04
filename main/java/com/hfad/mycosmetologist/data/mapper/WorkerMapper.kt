package com.hfad.mycosmetologist.data.mapper

import com.hfad.mycosmetologist.data.source.local.entity.ServiceDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.WorkerDbEntity
import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.entity.Worker

fun Worker.toDbModel(): WorkerDbEntity{
    return WorkerDbEntity(
        id = id,
        name = name,
        phone = phone,
        about = about,
        password = password,
        isActual = true)
}

fun WorkerDbEntity.toDomainModel(): Worker {
    return Worker(
        id = id,
        name = name,
        phone = phone,
        about = about,
        password = password)
}
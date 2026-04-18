package com.hfad.mycosmetologist.data.mapper

import com.hfad.mycosmetologist.data.source.local.entity.WorkerDbEntity
import com.hfad.mycosmetologist.data.source.remote.worker.WorkerRemoteEntity
import com.hfad.mycosmetologist.domain.entity.Worker

fun Worker.toDbModel(): WorkerDbEntity =
    WorkerDbEntity(
        id = id,
        name = name,
        phone = phone,
        about = about,
        isActual = true,
        specialization = specialization,
        email = email,
        experience = experience
    )

fun WorkerDbEntity.toDomainModel(): Worker =
    Worker(
        id = id,
        name = name,
        phone = phone,
        about = about,
        specialization = specialization,
        email = email,
        experience = experience,
    )

fun WorkerDbEntity.toRemoteEntity(): WorkerRemoteEntity =
    WorkerRemoteEntity(
        id = id,
        name = name,
        phone = phone,
        about = about,
        specialization = specialization,
        email = email,
        experience = experience,
        updatedAt = updatedAt
    )

fun WorkerRemoteEntity.toDbModel(): WorkerDbEntity =
    WorkerDbEntity(
        id = id,
        name = name,
        phone = phone,
        about = about,
        isActual = true,
        specialization = specialization,
        email = email,
        experience = experience,
        updatedAt = updatedAt,
        isSynced = isSynced
    )

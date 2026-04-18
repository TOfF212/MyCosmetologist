package com.hfad.mycosmetologist.data.mapper

import com.hfad.mycosmetologist.data.source.local.entity.ServiceDbEntity
import com.hfad.mycosmetologist.data.source.remote.service.ServiceRemoteEntity
import com.hfad.mycosmetologist.domain.entity.Service

fun Service.toDbModel(): ServiceDbEntity =
    ServiceDbEntity(
        id = id,
        workerId = workerId,
        name = name,
        price = price,
        durationMinutes = durationMinutes,
        description = description,
        isArchived = isArchived,
    )

fun ServiceDbEntity.toDomainModel(): Service =
    Service(
        id = id,
        workerId = workerId,
        name = name,
        price = price,
        durationMinutes = durationMinutes,
        description = description,
        isArchived = isArchived,
    )

fun ServiceDbEntity.toRemoteEntity(): ServiceRemoteEntity =
    ServiceRemoteEntity(
        id = id,
        workerId = workerId,
        name = name,
        price = price,
        durationMinutes = durationMinutes,
        description = description,
        isArchived = isArchived,
    )
fun ServiceRemoteEntity.toDbModel(): ServiceDbEntity =
    ServiceDbEntity(
        id = id,
        workerId = workerId,
        name = name,
        price = price,
        durationMinutes = durationMinutes,
        description = description,
        isArchived = isArchived,
    )

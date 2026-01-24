package com.hfad.mycosmetologist.data.mapper

import com.hfad.mycosmetologist.data.source.local.entity.ClientDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.ServiceDbEntity
import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.entity.Service

fun Service.toDbModel(): ServiceDbEntity{
    return ServiceDbEntity(
        id = id,
        workerId = workerId,
        name = name,
        price = price,
        durationMinutes = durationMinutes,
        description = description)
}

fun ServiceDbEntity.toDomainModel(): Service{
    return Service(
        id = id,
        workerId = workerId,
        name = name,
        price = price,
        durationMinutes = durationMinutes,
        description = description)
}
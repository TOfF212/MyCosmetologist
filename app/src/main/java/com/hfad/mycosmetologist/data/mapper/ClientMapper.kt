package com.hfad.mycosmetologist.data.mapper

import com.hfad.mycosmetologist.data.source.local.entity.ClientDbEntity
import com.hfad.mycosmetologist.data.source.remote.client.ClientRemoteEntity
import com.hfad.mycosmetologist.domain.entity.Client

fun Client.toDbModel(): ClientDbEntity =
    ClientDbEntity(
        id = id,
        workerId = workerId,
        name = name,
        phone = phone,
        about = about,
    )

fun ClientDbEntity.toDomainModel(): Client =
    Client(
        id = id,
        workerId = workerId,
        name = name,
        phone = phone,
        about = about,
    )

fun ClientDbEntity.toRemoteEntity(): ClientRemoteEntity =
    ClientRemoteEntity(
        id = id,
        workerId = workerId,
        name = name,
        phone = phone,
        about = about,
        updatedAt = updatedAt,
    )

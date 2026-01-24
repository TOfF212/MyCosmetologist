package com.hfad.mycosmetologist.data.mapper

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.hfad.mycosmetologist.data.source.local.entity.ClientDbEntity
import com.hfad.mycosmetologist.domain.entity.AppointmentStatus
import com.hfad.mycosmetologist.domain.entity.Client
import java.time.LocalDateTime

fun Client.toDbModel(): ClientDbEntity{
    return ClientDbEntity(
        id = id,
        workerId = workerId,
        name = name,
        phone = phone,
        about = about)
}

fun ClientDbEntity.toDomainModel(): Client{
    return Client(
        id = id,
        workerId = workerId,
        name = name,
        phone = phone,
        about = about
    )
}
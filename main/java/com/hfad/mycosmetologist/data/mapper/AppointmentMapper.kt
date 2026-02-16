package com.hfad.mycosmetologist.data.mapper

import com.hfad.mycosmetologist.data.source.local.entity.AppointmentDbEntity
import com.hfad.mycosmetologist.domain.entity.Appointment

fun Appointment.toDbModel(): AppointmentDbEntity =
    AppointmentDbEntity(
        id = id,
        workerId = workerId,
        clientId = clientId,
        description = description,
        startTime = startTime,
        endTime = endTime,
        cancelled = cancelled
    )

fun AppointmentDbEntity.toDomainModel(): Appointment =
    Appointment(
        id = id,
        workerId = workerId,
        clientId = clientId,
        description = description,
        startTime = startTime,
        endTime = endTime,
        servicesIds = mutableListOf<String>(),
        cancelled = cancelled
    )

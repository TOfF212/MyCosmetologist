package com.hfad.mycosmetologist.data.mapper

import com.hfad.mycosmetologist.data.source.local.entity.AppointmentDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentServiceDbEntity
import com.hfad.mycosmetologist.domain.entity.Appointment
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalQueries.zoneId

fun Appointment.toDbModel(): AppointmentDbEntity{
    return AppointmentDbEntity(
                id = id,
                workerId=workerId,
                clientId = clientId,
                status=status,
                description =description,
                startTime = startTime,
                endTime = endTime
            )
}

fun AppointmentDbEntity.toDomainModel(): Appointment{
    return Appointment(
        id = id,
        workerId = workerId,
        clientId = clientId,
        status = status,
        description =description,
        startTime = startTime,
        endTime = endTime,
        servicesIds = mutableListOf<String>()
    )
}


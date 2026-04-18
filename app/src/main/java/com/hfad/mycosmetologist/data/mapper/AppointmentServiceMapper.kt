package com.hfad.mycosmetologist.data.mapper

import com.hfad.mycosmetologist.data.source.local.entity.AppointmentServiceDbEntity
import com.hfad.mycosmetologist.data.source.remote.appointmentService.AppointmentServiceRemoteEntity

fun AppointmentServiceDbEntity.toRemoteEntity(): AppointmentServiceRemoteEntity =
    AppointmentServiceRemoteEntity(
        id = "${appointmentId}_$serviceId",
        appointmentId = appointmentId,
        serviceId = serviceId,
        updatedAt = updatedAt,
        isSynced = isSynced,
    )

package com.hfad.mycosmetologist.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "appointment_service",
    primaryKeys = ["appointment_id", "service_id"],
    indices = [Index("appointment_id")],
    foreignKeys = [
        ForeignKey(
            entity = AppointmentDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["appointment_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ServiceDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["service_id"],
            onDelete = ForeignKey.RESTRICT
        ),
    ]
)
data class AppointmentServiceDbEntity (
    @ColumnInfo(name = "appointment_id") val appointmentId: Long,
    @ColumnInfo(name = "service_id") val serviceId: Long,
){}
package com.hfad.mycosmetologist.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "appointments",
    indices = [
        Index("worker_id"),
        Index(value = ["worker_id", "start_time"]),
        Index("client_id"),
    ],
    foreignKeys = [
        ForeignKey(
            entity = WorkerDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["worker_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = ClientDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["client_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
)
data class AppointmentDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "worker_id") val workerId: String,
    @ColumnInfo(name = "client_id") val clientId: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "start_time") val startTime: Instant,
    @ColumnInfo(name = "end_time") val endTime: Instant,
    @ColumnInfo(name = "cancelled") val cancelled: Boolean
)

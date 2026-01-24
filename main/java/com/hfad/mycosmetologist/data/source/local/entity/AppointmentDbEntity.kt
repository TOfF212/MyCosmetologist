package com.hfad.mycosmetologist.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.hfad.mycosmetologist.domain.entity.AppointmentStatus
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    tableName = "appointments",
    indices = [
        Index("worker_id"),
        Index("start_time"),
        Index("end_time")
              ],
    foreignKeys = [
        ForeignKey(
            entity = WorkerDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["worker_id"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = ClientDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["client_id"],
            onDelete = ForeignKey.RESTRICT
        ),
    ]
)
data class AppointmentDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "worker_id") val workerId: String,
    @ColumnInfo(name = "client_id") val clientId: String,
    @ColumnInfo(name = "status") val status: AppointmentStatus,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "start_time") val startTime: LocalDateTime,
    @ColumnInfo(name = "end_time") val endTime: LocalDateTime
) {
}
package com.hfad.mycosmetologist.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "services",
    indices = [Index("worker_id")],
    foreignKeys = [
        ForeignKey(
            entity = WorkerDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["worker_id"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
)
data class ServiceDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "worker_id") val workerId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "duration_minutes") val durationMinutes: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "is_archived") val isArchived: Boolean = false,
)

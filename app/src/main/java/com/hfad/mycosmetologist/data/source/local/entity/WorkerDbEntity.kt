package com.hfad.mycosmetologist.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "workers",
)
data class WorkerDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "about") val about: String,
    @ColumnInfo(name = "is_actual") val isActual: Boolean,
    @ColumnInfo(name = "specialization") val specialization: String,
    @ColumnInfo(name = "experience") val experience: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Long = 0L,
    @ColumnInfo(name = "is_synced") val isSynced: Boolean = false,
    )

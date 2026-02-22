package com.hfad.mycosmetologist.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clients",
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
data class ClientDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "worker_id") val workerId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone")val phone: String,
    @ColumnInfo(name = "about") val about: String,
)

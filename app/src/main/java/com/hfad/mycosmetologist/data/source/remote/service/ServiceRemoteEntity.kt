package com.hfad.mycosmetologist.data.source.remote.service

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class ServiceRemoteEntity (
      val id: String,
      val workerId: String,
      val name: String,
      val price: Int,
      val durationMinutes: Int,
      val description: String,
      val isArchived: Boolean = false,
      val updatedAt: Long = 0L,
)

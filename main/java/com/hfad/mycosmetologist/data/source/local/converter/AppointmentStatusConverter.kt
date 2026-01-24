package com.hfad.mycosmetologist.data.source.local.converter

import androidx.room.TypeConverter
import com.hfad.mycosmetologist.domain.entity.AppointmentStatus
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class AppointmentStatusConverter {

    @TypeConverter
    fun fromString(value: String?): AppointmentStatus? {
        return value?.let {
            AppointmentStatus.valueOf(it)
        }
    }

    @TypeConverter
    fun dateToTimestamp(status: AppointmentStatus?): String? {
        return status?.name
    }
}
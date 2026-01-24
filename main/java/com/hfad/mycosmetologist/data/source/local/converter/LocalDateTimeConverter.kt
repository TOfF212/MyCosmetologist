package com.hfad.mycosmetologist.data.source.local.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? =
        value?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(it),
                ZoneOffset.UTC
            )
        }

    @TypeConverter
    fun toTimestamp(date: LocalDateTime?): Long? =
        date?.atZone(ZoneOffset.UTC)
            ?.toInstant()
            ?.toEpochMilli()
}
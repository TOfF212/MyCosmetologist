package com.hfad.mycosmetologist.data.source.local.converter

import androidx.room.TypeConverter
import com.hfad.mycosmetologist.domain.entity.AppointmentStatus

class AppointmentStatusConverter {
    @TypeConverter
    fun fromString(value: String?): AppointmentStatus? =
        value?.let {
            AppointmentStatus.valueOf(it)
        }

    @TypeConverter
    fun dateToTimestamp(status: AppointmentStatus?): String? = status?.name
}

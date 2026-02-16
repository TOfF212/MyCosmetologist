package com.hfad.mycosmetologist.presentation.util.entity

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.presentation.util.toMonthNameRes
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class PresentationAppointment(
    val clientName: String,
    val startTime: String,
    val endTime: String,
    val date: String,
    val services: String,
    val profit: String,
    val id: String,
    val cancelled: Boolean
) {


    companion object {
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        fun toPresentationAppointment(
            appointment: Appointment,
            servicesMap: Map<String, Service>,
            clientName: String,
        ): PresentationAppointment {
            val zone = ZoneId.systemDefault()

            val startZoned = appointment.startTime.atZone(zone)
            val endZoned = appointment.endTime.atZone(zone)

            val servicesNames =
                appointment.servicesIds
                    .mapNotNull { servicesMap[it]?.name }
                    .joinToString(", ")
            val appointmentProfit =
                appointment.servicesIds.mapNotNull { servicesMap[it]?.price }.sum()
            return PresentationAppointment(
                clientName = clientName,
                startTime = startZoned.format(timeFormatter),
                endTime = endZoned.format(timeFormatter),
                date = "${startZoned.dayOfMonth} ${startZoned.month.toMonthNameRes()} ${startZoned.year}",
                services = servicesNames,
                profit = appointmentProfit.toString(),
                id = appointment.id,
                cancelled = appointment.cancelled
            )
        }
    }
}

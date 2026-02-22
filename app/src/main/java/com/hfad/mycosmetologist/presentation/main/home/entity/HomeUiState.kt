package com.hfad.mycosmetologist.presentation.main.home.entity

import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment

sealed class HomeUiState {
    data class Success(
        val currentAppointmentsList: List<PresentationAppointment>,
        val pastAppointmentsList: List<PresentationAppointment>,
    ) : HomeUiState()

    object AllLoading : HomeUiState()

    data class CurrListLoading(
        val pastAppointmentsList: List<PresentationAppointment>,
    ) : HomeUiState()

    data class PastListLoading(
        val pastAppointmentsList: List<PresentationAppointment>,
    ) : HomeUiState()
}

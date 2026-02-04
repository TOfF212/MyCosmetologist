package com.hfad.mycosmetologist.presentation.main.home.entity

sealed class HomeUiState {
    data class Success(
        val currentAppointmentsList: List<HomeAppointment>,
        val pastAppointmentsList: List<HomeAppointment>
    ): HomeUiState()
    object AllLoading: HomeUiState()
    data class CurrListLoading(val pastAppointmentsList: List<HomeAppointment>): HomeUiState()
    data class PastListLoading(val pastAppointmentsList: List<HomeAppointment>): HomeUiState()
}
package com.hfad.mycosmetologist.presentation.main.profile.history.entity

import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment

sealed interface HistoryUiState {
    data object Loading : HistoryUiState

    data class Success(
        val appointments: List<PresentationAppointment>,
    ) : HistoryUiState
}

package com.hfad.mycosmetologist.presentation.main.profile.entity

sealed class ProfileUiState {
    data class Success(val data: Profile) : ProfileUiState()

    object Loading : ProfileUiState()
}

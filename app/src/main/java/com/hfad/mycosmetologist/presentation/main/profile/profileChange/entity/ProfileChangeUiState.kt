package com.hfad.mycosmetologist.presentation.main.profile.profileChange.entity

data class ProfileChangeUiState(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val specialization: String = "",
    val experience: String = "",
    val about: String = "",
    val isLoading: Boolean = true,
)

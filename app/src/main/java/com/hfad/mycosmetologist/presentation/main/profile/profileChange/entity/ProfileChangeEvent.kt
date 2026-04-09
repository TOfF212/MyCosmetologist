package com.hfad.mycosmetologist.presentation.main.profile.profileChange.entity

sealed class ProfileChangeEvent {
    object NavigateBack : ProfileChangeEvent()

    data class Error(val message: String) : ProfileChangeEvent()
}

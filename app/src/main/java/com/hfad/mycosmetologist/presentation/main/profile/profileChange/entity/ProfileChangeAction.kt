package com.hfad.mycosmetologist.presentation.main.profile.profileChange.entity

sealed class ProfileChangeAction {
    data class OnNameChanged(val value: String) : ProfileChangeAction()

    data class OnPhoneChanged(val value: String) : ProfileChangeAction()

    data class OnEmailChanged(val value: String) : ProfileChangeAction()

    data class OnSpecializationChanged(val value: String) : ProfileChangeAction()

    data class OnExperienceChanged(val value: String) : ProfileChangeAction()

    data class OnAboutChanged(val value: String) : ProfileChangeAction()

    object OnSaveClicked : ProfileChangeAction()
}

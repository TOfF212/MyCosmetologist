package com.hfad.mycosmetologist.presentation.main.appointment.create

import androidx.lifecycle.ViewModel
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import dagger.assisted.AssistedFactory
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = AppointmentCreateViewModel.Factory::class)
class AppointmentCreateViewModel() : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(appScreen: AppScreen.AppointmentCreate): AppointmentCreateViewModel
    }
}

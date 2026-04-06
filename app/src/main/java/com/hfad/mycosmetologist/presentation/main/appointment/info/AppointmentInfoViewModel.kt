package com.hfad.mycosmetologist.presentation.main.appointment.info

import androidx.lifecycle.ViewModel
import com.hfad.mycosmetologist.domain.useCase.appointment.GetAppointment
import com.hfad.mycosmetologist.presentation.main.appointment.create.AppointmentCreateViewModel
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = AppointmentInfoViewModel.Factory::class)

class AppointmentInfoViewModel @AssistedInject constructor(
    @Assisted private val appScreen: AppScreen.AppointmentInfo
    private val getAppointment: GetAppointment
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(appScreen: AppScreen.AppointmentInfo): AppointmentInfoViewModel
    }
}

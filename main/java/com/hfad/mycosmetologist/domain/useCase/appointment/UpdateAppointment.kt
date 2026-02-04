package com.hfad.mycosmetologist.domain.useCase.appointment

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import com.hfad.mycosmetologist.domain.exceptions.InvalidAppointmentTimeException
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.hfad.mycosmetologist.domain.util.Result
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class UpdateAppointment @Inject constructor(private val repository: AppointmentRepository) {

        operator  fun invoke(
            appointment: Appointment
        ): Flow<Result<Unit>> {

            return repository.isTimeBusy(appointment)
                .flatMapLatest { result ->
                    when (result) {

                        is Result.Loading -> {
                            flowOf(Result.Loading)
                        }

                        is Result.Success -> {
                            if (result.data) {
                                flowOf(
                                    Result.Error(
                                        InvalidAppointmentTimeException("InvalidTime")
                                    )
                                )
                            } else {
                                repository.updateAppointment(appointment)
                            }
                        }

                        is Result.Error -> {
                            flowOf(Result.Error(result.exception))
                        }
                    }
                }
        }
    }

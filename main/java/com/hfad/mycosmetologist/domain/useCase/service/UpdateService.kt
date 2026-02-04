package com.hfad.mycosmetologist.domain.useCase.service

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.exceptions.ObjectIsAlreadyExistException
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import com.hfad.mycosmetologist.domain.util.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class UpdateService
    @Inject
    constructor(
        private val repository: ServiceRepository,
    ) {
        operator fun invoke(service: Service): Flow<Result<Unit>> =
            repository
                .serviceIsExist(service)
                .flatMapLatest { result ->
                    when (result) {
                        is Result.Loading -> {
                            flowOf(Result.Loading)
                        }

                        is Result.Success -> {
                            if (result.data) {
                                flowOf(
                                    Result.Error(
                                        ObjectIsAlreadyExistException(
                                            "Client is already exist",
                                        ),
                                    ),
                                )
                            } else {
                                repository.updateService(service)
                            }
                        }

                        is Result.Error -> {
                            flowOf(Result.Error(result.exception))
                        }
                    }
                }
    }

package com.hfad.mycosmetologist.domain.useCase.service

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.exceptions.ObjectIsAlreadyExistException
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import com.hfad.mycosmetologist.domain.util.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class CreateService @Inject constructor(private val repository: ServiceRepository){

    suspend operator fun invoke(service: Service): Flow<Result<Unit>> {
        return repository.serviceIsExist(service)
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
                                        "Client is already exist"
                                    )
                                )
                            )
                        } else {
                            repository.createService(service)
                        }
                    }

                    is Result.Error -> {
                        flowOf(Result.Error(result.exception))
                    }
                }
            }
    }
}

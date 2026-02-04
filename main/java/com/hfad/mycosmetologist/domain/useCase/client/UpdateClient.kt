package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.exceptions.ObjectIsAlreadyExistException
import com.hfad.mycosmetologist.domain.repository.ClientRepository
import com.hfad.mycosmetologist.domain.util.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class UpdateClient @Inject constructor(private val repository: ClientRepository) {

     operator fun invoke(client: Client): Flow<Result<Unit>> {
        return repository.clientIsExists(client)
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
                            repository.updateClient(client)
                        }
                    }

                    is Result.Error -> {
                        flowOf(Result.Error(result.exception))
                    }
                }
            }
    }
}
package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.repository.ClientRepository
import jakarta.inject.Inject

class GetClientList
    @Inject
    constructor(
        private val repository: ClientRepository,
    ) {
        operator fun invoke(workerId: String) = repository.getClientList(workerId)
    }

package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.repository.ClientRepository
import jakarta.inject.Inject

class GetClient
    @Inject
    constructor(
        private val repository: ClientRepository,
    ) {
        operator fun invoke(
            workerId: String,
            clientId: String,
        ) = repository.getClient(workerId, clientId)
    }

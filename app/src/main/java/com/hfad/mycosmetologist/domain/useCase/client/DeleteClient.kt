package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.repository.ClientRepository
import jakarta.inject.Inject

class DeleteClient
    @Inject
    constructor(
        private val repository: ClientRepository,
    ) {
        operator fun invoke(client: Client) = repository.deleteClient(client)
    }

package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.repository.ClientRepository

class GetClient(private val repository: ClientRepository) {

    suspend operator fun invoke(workerId: String, clientId: String): Client? {
        return repository.getClient(workerId,clientId)
    }

}
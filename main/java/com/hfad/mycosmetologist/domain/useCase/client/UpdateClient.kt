package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.repository.ClientRepository
import com.hfad.mycosmetologist.domain.usecases.client.UpdateClientResult

class UpdateClient(private val repository: ClientRepository) {

    suspend operator fun invoke(client: Client): UpdateClientResult {

        if(repository.clientIsExists(client)){
            return UpdateClientResult.ClientAlreadyExists
        }
        repository.updateClient(client)
        return UpdateClientResult.Success
    }
}
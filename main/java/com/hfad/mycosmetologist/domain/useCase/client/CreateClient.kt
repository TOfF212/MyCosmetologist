package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.repository.ClientRepository
import com.hfad.mycosmetologist.domain.usecases.client.CreateClientResult

class CreateClient( private val repository: ClientRepository) {



    suspend operator fun invoke(client: Client): CreateClientResult {
        if(repository.clientIsExists(client)){
            return CreateClientResult.ClientAlreadyExists
        }
        repository.createClient(client)
        return CreateClientResult.Success
    }
}
package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.repository.ClientRepository

class DeleteClient(private val repository: ClientRepository) {
    suspend operator fun invoke(client: Client){
        repository.deleteClient(client)
    }
}
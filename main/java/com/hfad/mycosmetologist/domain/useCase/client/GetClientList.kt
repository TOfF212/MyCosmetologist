package com.hfad.mycosmetologist.domain.useCase.client

import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.repository.ClientRepository

class GetClientList(private val repository: ClientRepository) {
    suspend operator fun invoke(workerId: Long): List<Client>{
        return repository.getClientList(workerId)
    }
}
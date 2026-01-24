package com.hfad.mycosmetologist.domain.useCase.service

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.repository.ServiceRepository

class DeleteService(private val repository: ServiceRepository) {
    suspend operator fun invoke(service: Service){
        repository.deleteService(service)
    }
}
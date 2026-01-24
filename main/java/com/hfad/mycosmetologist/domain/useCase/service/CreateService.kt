package com.hfad.mycosmetologist.domain.useCase.service

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import com.hfad.mycosmetologist.domain.usecases.service.CreateServiceResult

class CreateService (private val repository: ServiceRepository){

    suspend operator fun invoke(service: Service): CreateServiceResult {

        if (repository.serviceIsExist(service)) return CreateServiceResult.InvalidName

        repository.createService(service)

        return CreateServiceResult.Success
    }
}
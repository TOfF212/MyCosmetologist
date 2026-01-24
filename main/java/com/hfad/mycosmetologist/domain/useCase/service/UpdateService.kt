package com.hfad.mycosmetologist.domain.useCase.service

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import com.hfad.mycosmetologist.domain.usecases.service.UpdateServiceResult

class UpdateService (private val repository: ServiceRepository){

    suspend operator fun invoke(service: Service): UpdateServiceResult {

        if (repository.serviceIsExist(service)) return UpdateServiceResult.InvalidName

        repository.updateService(service)

        return UpdateServiceResult.Success
    }
}
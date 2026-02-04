package com.hfad.mycosmetologist.domain.useCase.service

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import jakarta.inject.Inject

class DeleteService
    @Inject
    constructor(
        private val repository: ServiceRepository,
    ) {
        operator fun invoke(service: Service) = repository.deleteService(service)
    }

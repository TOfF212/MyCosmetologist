package com.hfad.mycosmetologist.domain.useCase.service

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.repository.ServiceRepository

class GetPriceList(private val repository: ServiceRepository){

    suspend operator fun invoke(workerId: String): List<Service>{
        return repository.getPriceList(workerId)
    }
}
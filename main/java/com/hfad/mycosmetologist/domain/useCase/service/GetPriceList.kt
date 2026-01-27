package com.hfad.mycosmetologist.domain.useCase.service

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import jakarta.inject.Inject

class GetPriceList @Inject constructor(private val repository: ServiceRepository){

    suspend operator fun invoke(workerId: String)= repository.getPriceList(workerId)

}
package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Service

interface ServiceRepository {

    suspend fun createService(service: Service)

    suspend fun getPriceList(workerId: String): List<Service>

    suspend fun updateService(service: Service)

    suspend fun deleteService(service: Service)

    suspend fun serviceIsExist(service: Service): Boolean
}
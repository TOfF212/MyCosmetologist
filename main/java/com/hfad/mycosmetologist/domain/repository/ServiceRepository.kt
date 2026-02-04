package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun createService(service: Service): Flow<Result<Unit>>

    fun getPriceList(workerId: String): Flow<Result<List<Service>>>

    fun updateService(service: Service): Flow<Result<Unit>>

    fun deleteService(service: Service): Flow<Result<Unit>>

    fun serviceIsExist(service: Service): Flow<Result<Boolean>>

    fun getService(
        workerId: String,
        serviceId: String,
    ): Flow<Result<Service>>
}

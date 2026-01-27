package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.entity.Service
import kotlinx.coroutines.flow.Flow
import com.hfad.mycosmetologist.domain.util.Result

interface ServiceRepository {

    suspend fun createService(service: Service): Flow<Result<Unit>>

    suspend fun getPriceList(workerId: String): Flow<Result<List<Service>>>

    suspend fun updateService(service: Service): Flow<Result<Unit>>

    suspend fun deleteService(service: Service): Flow<Result<Unit>>

    suspend fun serviceIsExist(service: Service): Flow<Result<Boolean>>
}
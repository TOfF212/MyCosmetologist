package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.dao.ServiceDao
import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.repository.ServiceRepository

class ServiceRepositoryImpl(private val serviceDao: ServiceDao): ServiceRepository {
    override suspend fun createService( service: Service){
        serviceDao.insert(service.toDbModel())
    }



    override suspend fun updateService(service: Service){
        serviceDao.update(service.toDbModel())
    }

    override suspend fun deleteService(service: Service){
        serviceDao.delete(service.toDbModel())
    }

    override suspend fun getPriceList(workerId: String): List<Service>{
        val result = mutableListOf<Service>()
        serviceDao.getListByWorkerId(workerId).forEach { result.add(it.toDomainModel()) }
        return result.toList()
    }

    override suspend fun serviceIsExist(service: Service): Boolean{
        val client = serviceDao.getByName(service.name,  service.workerId)
        return when (client){
            null -> false
            else -> true
        }
    }
}
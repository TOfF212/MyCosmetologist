package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.dao.ServiceDao
import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import com.hfad.mycosmetologist.domain.util.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ServiceRepositoryImpl @Inject constructor(private val serviceDao: ServiceDao): ServiceRepository {

    override suspend fun createService( service: Service): Flow<Result<Unit>> = flow{
        emit(Result.Loading)
        try{
            serviceDao.insert(service.toDbModel())
            emit(Result.Success(Unit))
        } catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }



    override suspend fun updateService(service: Service): Flow<Result<Unit>> = flow{
        emit(Result.Loading)
        try{
            serviceDao.update(service.toDbModel())
            emit(Result.Success(Unit))
        } catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }

    override suspend fun deleteService(service: Service): Flow<Result<Unit>> = flow{
        emit(Result.Loading)
        try{
            serviceDao.delete(service.toDbModel())
            emit(Result.Success(Unit))
        } catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }

    override suspend fun getPriceList(workerId: String): Flow<Result<List<Service>>> = flow{
        emit(Result.Loading)

        try{
            val result = mutableListOf<Service>()
            serviceDao.getListByWorkerId(workerId).forEach { result.add(it.toDomainModel()) }
            emit(Result.Success(result.toList()))
        }catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }

    override suspend fun serviceIsExist(service: Service): Flow<Result<Boolean>> = flow{
        emit(Result.Loading)

        try {
            val service = serviceDao.getByName(service.name, service.workerId)
            val result = when (service) {
                null -> false
                else -> true
            }
            emit( Result.Success(result))
        }catch (e: Exception){
            emit(Result.Error(e))
        }

    }.catch{ e->
        emit(Result.Error(e))
    }

}
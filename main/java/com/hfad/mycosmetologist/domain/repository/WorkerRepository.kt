package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Worker
import kotlinx.coroutines.flow.Flow
import com.hfad.mycosmetologist.domain.util.Result

interface WorkerRepository {

    suspend fun getWorkerById(id: String): Flow<Result<Worker>>

    suspend fun createWorker(worker: Worker): Flow<Result<Unit>>

    suspend fun updateWorker(worker: Worker): Flow<Result<Unit>>

    suspend fun deleteWorker(worker: Worker): Flow<Result<Unit>>

    suspend fun workerIsExist(): Flow<Result<Boolean>>
}
package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Worker
import kotlinx.coroutines.flow.Flow
import com.hfad.mycosmetologist.domain.util.Result

interface WorkerRepository {

     fun getWorkerById(id: String): Flow<Result<Worker>>

     fun getActualWorker():  Flow<Result<Worker>>

     fun createWorker(worker: Worker): Flow<Result<Unit>>

     fun updateWorker(worker: Worker): Flow<Result<Unit>>

     fun deleteWorker(worker: Worker): Flow<Result<Unit>>

     fun workerIsExist(): Flow<Result<Boolean>>
}
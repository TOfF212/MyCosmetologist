package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.dao.WorkerDao
import com.hfad.mycosmetologist.domain.entity.Worker
import com.hfad.mycosmetologist.domain.repository.WorkerRepository
import com.hfad.mycosmetologist.domain.util.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class WorkerRepositoryImpl
@Inject
constructor(
    private val workerDao: WorkerDao,
) : WorkerRepository {
    override fun getWorkerById(id: String): Flow<Result<Worker>> =
        flow {
            emit(Result.Loading)

            try {
                val worker = workerDao.getById(id)?.toDomainModel()
                emit(Result.Success(worker!!))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }

    override fun createWorker(worker: Worker): Flow<Result<Unit>> =
        flow {
            emit(Result.Loading)
            try {
                workerDao.insert(worker.toDbModel())
                emit(Result.Success(Unit))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }

    override fun updateWorker(worker: Worker): Flow<Result<Unit>> =
        flow {
            emit(Result.Loading)
            try {
                workerDao.update(worker.toDbModel())
                emit(Result.Success(Unit))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }

    override fun deleteWorker(worker: Worker): Flow<Result<Unit>> =
        flow {
            emit(Result.Loading)
            try {
                workerDao.delete(worker.toDbModel())
                emit(Result.Success(Unit))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }

    override fun workerIsExist(): Flow<Result<Boolean>> =
        flow {
            emit(Result.Loading)
            try {
                val workers = workerDao.getActualWorker()
                if (workers.isEmpty()) {
                    emit(Result.Success(false))
                } else if (workers.size > 1) {
                    workers.forEach { worker ->
                        workerDao.update(worker.copy(isActual = false))
                    }
                    emit(Result.Success(false))
                } else {
                    emit(Result.Success(true))
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }

    override fun getActualWorker(): Flow<Result<Worker>> =
        flow {
            emit(Result.Loading)

            try {

                emit(Result.Success(workerDao.getActualWorker()[0].toDomainModel()))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }.catch { e ->
            emit(Result.Error(e))
        }
}

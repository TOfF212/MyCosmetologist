package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.dao.WorkerDao
import com.hfad.mycosmetologist.domain.entity.Worker
import com.hfad.mycosmetologist.domain.repository.WorkerRepository
import jakarta.inject.Inject

class WorkerRepositoryImpl @Inject constructor(private val workerDao: WorkerDao): WorkerRepository {
    override suspend fun getWorkerById(id: String): Worker? {
        return workerDao.getById(id)?.toDomainModel()
    }

    override suspend fun createWorker(worker: Worker) {
        workerDao.insert(worker.toDbModel())
    }

    override suspend fun updateWorker(worker: Worker) {
        workerDao.update(worker.toDbModel())
    }

    override suspend fun deleteWorker(worker: Worker) {
        workerDao.delete(worker.toDbModel())

    }

}
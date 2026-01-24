package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Worker

interface WorkerRepository {

    suspend fun getWorkerById(id: String): Worker?

    suspend fun createWorker(worker: Worker)

    suspend fun updateWorker(worker: Worker)

    suspend fun deleteWorker(worker: Worker)
}
package com.hfad.mycosmetologist.domain.useCase.worker

import com.hfad.mycosmetologist.domain.entity.Worker
import com.hfad.mycosmetologist.domain.repository.WorkerRepository
import jakarta.inject.Inject

class UpdateWorker @Inject constructor(private val repository: WorkerRepository){

    suspend operator fun invoke(worker: Worker) = repository.updateWorker(worker)


}

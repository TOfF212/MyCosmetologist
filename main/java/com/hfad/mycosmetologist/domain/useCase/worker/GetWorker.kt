package com.hfad.mycosmetologist.domain.useCase.worker

import com.hfad.mycosmetologist.domain.entity.Worker
import com.hfad.mycosmetologist.domain.repository.WorkerRepository
import jakarta.inject.Inject

class GetWorker @Inject constructor(private val repository: WorkerRepository){

    suspend operator fun invoke(id: String) = repository.getWorkerById(id)


}
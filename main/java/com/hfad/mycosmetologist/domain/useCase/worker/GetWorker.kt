package com.hfad.mycosmetologist.domain.useCase.worker

import com.hfad.mycosmetologist.domain.entity.Worker
import com.hfad.mycosmetologist.domain.repository.WorkerRepository

class GetWorker (private val repository: WorkerRepository){

    suspend operator fun invoke(id: String): Worker? {


        return repository.getWorkerById(id)
    }

}
package com.hfad.mycosmetologist.domain.useCase.worker

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.entity.Worker
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import com.hfad.mycosmetologist.domain.repository.WorkerRepository
import jakarta.inject.Inject

class CreateWorker @Inject constructor(private val repository: WorkerRepository){

        suspend operator fun invoke(worker: Worker) = repository.createWorker(worker)


}
package com.hfad.mycosmetologist.domain.useCase.worker

import com.hfad.mycosmetologist.domain.repository.WorkerRepository
import jakarta.inject.Inject

class
GetActualWorker@Inject
    constructor(
        private val repository: WorkerRepository,
    ) {
        operator fun invoke() = repository.getActualWorker()
    }

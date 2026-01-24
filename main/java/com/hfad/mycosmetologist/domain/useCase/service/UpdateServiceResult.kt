package com.hfad.mycosmetologist.domain.useCase.service

sealed class UpdateServiceResult {
    object Success: UpdateServiceResult()
    object InvalidName: UpdateServiceResult()
}
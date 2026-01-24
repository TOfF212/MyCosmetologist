package com.hfad.mycosmetologist.domain.useCase.client

sealed class UpdateClientResult {
    object Success: UpdateClientResult()
    object ClientAlreadyExists: UpdateClientResult()
}
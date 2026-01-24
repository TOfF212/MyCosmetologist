package com.hfad.mycosmetologist.domain.useCase.client

sealed class CreateClientResult {
    object Success: CreateClientResult()
    object ClientAlreadyExists: CreateClientResult()
}
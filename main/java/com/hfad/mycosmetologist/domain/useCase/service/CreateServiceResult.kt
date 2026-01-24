package com.hfad.mycosmetologist.domain.useCase.service

sealed class CreateServiceResult {
    object Success: CreateServiceResult()
    object InvalidName: CreateServiceResult()
}
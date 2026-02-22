package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun createClient(client: Client): Flow<Result<Unit>>

    fun updateClient(client: Client): Flow<Result<Unit>>

    fun deleteClient(client: Client): Flow<Result<Unit>>

    fun getClient(
        workerId: String,
        clientId: String,
    ): Flow<Result<Client>>

    fun getClientList(workerId: String): Flow<Result<List<Client>>>

    fun clientIsExists(client: Client): Flow<Result<Boolean>>
}

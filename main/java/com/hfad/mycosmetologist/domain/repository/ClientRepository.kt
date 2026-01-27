package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.entity.Client
import kotlinx.coroutines.flow.Flow
import com.hfad.mycosmetologist.domain.util.Result

interface ClientRepository {

    suspend fun createClient(client: Client): Flow<Result<Unit>>

    suspend fun updateClient(client: Client): Flow<Result<Unit>>

    suspend fun deleteClient(client: Client): Flow<Result<Unit>>

    suspend fun getClient(workerId: String, clientId: String): Flow<Result<Client>>

    suspend fun getClientList(workerId: String): Flow<Result<List<Client>>>

    suspend fun clientIsExists(client: Client): Flow<Result<Boolean>>
}
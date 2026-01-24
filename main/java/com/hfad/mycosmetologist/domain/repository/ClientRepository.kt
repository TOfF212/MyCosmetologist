package com.hfad.mycosmetologist.domain.repository

import com.hfad.mycosmetologist.domain.entity.Client

interface ClientRepository {

    suspend fun createClient(client: Client)

    suspend fun updateClient(client: Client)

    suspend fun deleteClient(client: Client)

    suspend fun getClient(workerId: String, clientId: String): Client?

    suspend fun getClientList(workerId: String): List<Client>

    suspend fun clientIsExists(client: Client): Boolean
}
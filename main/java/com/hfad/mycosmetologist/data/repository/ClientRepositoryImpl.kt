package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.dao.ClientDao
import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.repository.ClientRepository

class ClientRepositoryImpl(private val clientDao: ClientDao): ClientRepository {
    override suspend fun createClient(client: Client){
        clientDao.insert(client.toDbModel())
    }

    override suspend fun updateClient(client: Client){
        clientDao.update(client.toDbModel())
    }

    override suspend fun deleteClient(client: Client){
        clientDao.delete(client.toDbModel())
    }

    override suspend fun getClient(workerId: String, clientId: String): Client?{
        return clientDao.getById(clientId, workerId)?.toDomainModel()
    }

    override suspend fun getClientList(workerId: String): List<Client>{
        val result = mutableListOf<Client>()
        clientDao.getListByWorkerId(workerId).forEach { result.add(it.toDomainModel()) }
        return result.toList()
    }

    override suspend fun clientIsExists(client: Client): Boolean{
        val client = clientDao.getByParams(client.name, client.phone, client.workerId)
        return when (client){
            null -> false
            else -> true
        }
    }
}
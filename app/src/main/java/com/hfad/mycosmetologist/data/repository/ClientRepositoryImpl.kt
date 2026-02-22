package com.hfad.mycosmetologist.data.repository

import com.hfad.mycosmetologist.data.mapper.toDbModel
import com.hfad.mycosmetologist.data.mapper.toDomainModel
import com.hfad.mycosmetologist.data.source.local.db.dao.ClientDao
import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.repository.ClientRepository
import com.hfad.mycosmetologist.domain.util.Result
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ClientRepositoryImpl
    @Inject
    constructor(
        private val clientDao: ClientDao,
    ) : ClientRepository {
        override fun createClient(client: Client): Flow<Result<Unit>> =
            flow {
                emit(Result.Loading)
                try {
                    clientDao.insert(client.toDbModel())
                    emit(Result.Success(Unit))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }.catch { e ->
                emit(Result.Error(e))
            }

        override fun updateClient(client: Client): Flow<Result<Unit>> =
            flow {
                emit(Result.Loading)
                try {
                    clientDao.update(client.toDbModel())
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }.catch { e ->
                emit(Result.Error(e))
            }

        override fun deleteClient(client: Client): Flow<Result<Unit>> =
            flow {
                emit(Result.Loading)
                try {
                    clientDao.delete(client.toDbModel())
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }.catch { e ->
                emit(Result.Error(e))
            }

        override fun getClient(
            workerId: String,
            clientId: String,
        ): Flow<Result<Client>> =
            flow {
                emit(Result.Loading)
                try {
                    val client = clientDao.getById(clientId, workerId)?.toDomainModel()
                    emit(Result.Success(client!!))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }.catch { e ->
                emit(Result.Error(e))
            }

        override fun getClientList(workerId: String): Flow<Result<List<Client>>> =
            flow {
                emit(Result.Loading)
                try {
                    val result = mutableListOf<Client>()
                    clientDao.getListByWorkerId(workerId).forEach { result.add(it.toDomainModel()) }
                    emit(Result.Success(result.toList()))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }.catch { e ->
                emit(Result.Error(e))
            }

        override fun clientIsExists(client: Client): Flow<Result<Boolean>> =
            flow {
                emit(Result.Loading)

                try {
                    val client = clientDao.getByParams(client.name, client.phone, client.workerId)
                    val result =
                        when (client) {
                            null -> false
                            else -> true
                        }

                    emit(Result.Success(result))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }.catch { e ->
                emit(Result.Error(e))
            }
    }

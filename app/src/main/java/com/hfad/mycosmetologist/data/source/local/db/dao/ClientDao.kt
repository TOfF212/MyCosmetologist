package com.hfad.mycosmetologist.data.source.local.db.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hfad.mycosmetologist.data.source.local.entity.ClientDbEntity

@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: ClientDbEntity)

    @Update
    suspend fun update(client: ClientDbEntity)

    @Delete
    suspend fun delete(client: ClientDbEntity)

    @Query(
        """
        SELECT * FROM clients
        WHERE id = :clientId 
        AND worker_id = :workerId        
    """,
    )
    suspend fun getById(
        clientId: String,
        workerId: String,
    ): ClientDbEntity?

    @Query(
        """
        SELECT * FROM clients
        WHERE worker_id = :workerId        
    """,
    )
    suspend fun getListByWorkerId(workerId: String): List<ClientDbEntity>

    @Query(
        """
        SELECT * FROM clients
        WHERE name = :name
        AND phone = :phone
        AND worker_id = :workerId
    """,
    )
    suspend fun getByParams(
        name: String,
        phone: String,
        workerId: String,
    ): ClientDbEntity?
}

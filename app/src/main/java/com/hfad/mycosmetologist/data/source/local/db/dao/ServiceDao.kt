package com.hfad.mycosmetologist.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hfad.mycosmetologist.data.source.local.entity.ServiceDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.WorkerDbEntity
import com.hfad.mycosmetologist.domain.entity.Service

@Dao
interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(service: ServiceDbEntity)

    @Update
    suspend fun update(service: ServiceDbEntity)

    @Delete
    suspend fun delete(service: ServiceDbEntity)

    @Query(
        """
        SELECT * FROM services
        WHERE worker_id = :workerId
    """,
    )
    suspend fun getListByWorkerId(workerId: String): List<ServiceDbEntity>

    @Query(
        """
        SELECT * FROM services
        WHERE id = :serviceId
        AND worker_id = :workerId
        
    """,
    )
    suspend fun getById(
        workerId: String,
        serviceId: String,
    ): ServiceDbEntity?

    @Query(
        """
        UPDATE services
        SET is_archived = 1
        WHERE id = :serviceId
        AND worker_id = :workerId
    """,
    )
    suspend fun archive(serviceId: String, workerId: String)

    @Query(
        """
        SELECT * FROM services
        WHERE name = :name
        AND worker_id = :workerId
    """,
    )
    suspend fun getByName(
        name: String,
        workerId: String,
    ): ServiceDbEntity?

    @Query("SELECT * FROM services WHERE is_synced = 0")
    suspend fun getUnsynced(): List<ServiceDbEntity>
}

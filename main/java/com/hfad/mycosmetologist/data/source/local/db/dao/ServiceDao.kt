package com.hfad.mycosmetologist.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.ServiceDbEntity

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(service: ServiceDbEntity)

    @Update
    suspend fun update(service: ServiceDbEntity)

    @Delete
    suspend fun delete(service: ServiceDbEntity)

    @Query("""
        SELECT * FROM services
        WHERE worker_id = :workerId
    """)
    suspend fun getListByWorkerId(
        workerId: Long
    ): List<ServiceDbEntity>

    @Query("""
        SELECT * FROM services
        WHERE id = :serviceId
        AND worker_id = :workerId
        
    """)
    suspend fun getById(
        workerId: Long,
        serviceId: Long
    ): ServiceDbEntity?


}
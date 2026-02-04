package com.hfad.mycosmetologist.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.WorkerDbEntity

@Dao
interface WorkerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(worker: WorkerDbEntity)

    @Update
    suspend fun update(worker: WorkerDbEntity)

    @Delete
    suspend fun delete(worker: WorkerDbEntity)

    @Query("""
        SELECT * FROM workers
        WHERE id = :workerId
    """) suspend fun getById(
        workerId: String
    ): WorkerDbEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM workers)")
    suspend fun hasAnyWorker(): Boolean

    @Query("""
        SELECT * FROM workers
        WHERE is_actual = true
    """)
    suspend fun getActualWorker(): WorkerDbEntity?
}
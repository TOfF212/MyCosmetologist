package com.hfad.mycosmetologist.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentServiceDbEntity

@Dao
interface AppointmentServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: AppointmentServiceDbEntity)

    @Update
    suspend fun update(entity: AppointmentServiceDbEntity)

    @Delete
    suspend fun delete(entity: AppointmentServiceDbEntity)

    @Query(
        """
        DELETE FROM appointment_service
        WHERE appointment_id = :appointmentId
    """,
    )
    suspend fun deleteByAppointmentId(appointmentId: String)

    @Query(
        """
        SELECT * FROM appointment_service
        WHERE appointment_id = :appointmentId
    """,
    )
    suspend fun getByAppointmentId(appointmentId: String): List<AppointmentServiceDbEntity>
}

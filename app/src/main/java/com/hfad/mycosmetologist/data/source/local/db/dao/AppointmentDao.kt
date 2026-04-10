package com.hfad.mycosmetologist.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentDbEntity

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: AppointmentDbEntity)

    @Update
    suspend fun update(appointment: AppointmentDbEntity)

    @Delete
    suspend fun delete(appointment: AppointmentDbEntity)

    @Query(
        """
        SELECT * FROM appointments 
        WHERE worker_id = :workerId 
        AND id = :appointmentId
    """,
    )
    suspend fun getById(
        workerId: String,
        appointmentId: String,
    ): AppointmentDbEntity?

    @Query(
        """
        SELECT * FROM appointments
        WHERE worker_id = :workerId
        AND start_time BETWEEN :start AND :end
        ORDER BY start_time ASC
    """,
    )

    suspend fun getByDate(
        workerId: String,
        start: Long,
        end: Long,
    ): List<AppointmentDbEntity>
    @Query(
        """
        SELECT * FROM appointments
        WHERE worker_id = :workerId
        AND end_time > :now
        ORDER BY start_time ASC
        LIMIT 20
    """,
    )
    suspend fun get20Upcoming(
        workerId: String,
        now: Long,
    ): List<AppointmentDbEntity>
    @Query(
        """
        SELECT * FROM appointments
        WHERE worker_id = :workerId
        AND end_time < :now
        ORDER BY start_time DESC
    """,
    )
    suspend fun getPast(
        workerId: String,
        now: Long,
    ): List<AppointmentDbEntity>

    @Query(
        """
        SELECT * FROM appointments
        WHERE worker_id = :workerId
        AND end_time < :now
        ORDER BY start_time DESC
        LIMIT 20
    """,
    )
    suspend fun get20Last(
        workerId: String,
        now: Long,
    ): List<AppointmentDbEntity>

    @Query(
        """
        SELECT * FROM appointments
        WHERE worker_id = :workerId
        AND start_time < :now
        ORDER BY start_time DESC
        LIMIT 1
    """,
    )
    suspend fun getPrevious(
        workerId: String,
        now: Long,
    ): AppointmentDbEntity?

    @Query(
        """
        SELECT * FROM appointments
        WHERE worker_id = :workerId
        AND client_id = :clientId
        ORDER BY start_time DESC
        LIMIT 20
    """,
    )
    suspend fun getByClientId(
        workerId: String,
        clientId: String,
    ): List<AppointmentDbEntity>

    @Query(
        """
        SELECT * FROM appointments
        WHERE worker_id = :workerId
        AND start_time > :now
        ORDER BY start_time ASC
        LIMIT 1
    """,
    )
    suspend fun getNext(
        workerId: String,
        now: Long,
    ): AppointmentDbEntity?
}

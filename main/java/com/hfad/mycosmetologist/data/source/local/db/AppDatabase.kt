package com.hfad.mycosmetologist.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.AppointmentServiceDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.ClientDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.ServiceDbEntity
import com.hfad.mycosmetologist.data.source.local.entity.WorkerDbEntity
import com.hfad.mycosmetologist.data.source.local.converter.AppointmentStatusConverter
import com.hfad.mycosmetologist.data.source.local.converter.InstantConverter
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentDao
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.local.db.dao.ClientDao
import com.hfad.mycosmetologist.data.source.local.db.dao.ServiceDao
import com.hfad.mycosmetologist.data.source.local.db.dao.WorkerDao

@Database(
    entities = [
        AppointmentDbEntity::class,
        AppointmentServiceDbEntity::class,
        ClientDbEntity::class,
        ServiceDbEntity::class,
        WorkerDbEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    InstantConverter::class,
    AppointmentStatusConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao
    abstract fun clientDao(): ClientDao
    abstract fun serviceDao(): ServiceDao
    abstract fun appointmentServiceDao(): AppointmentServiceDao
    abstract fun workerDao(): WorkerDao

}
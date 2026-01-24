package com.hfad.mycosmetologist.data.di

import android.content.Context
import androidx.room.Room
import com.hfad.mycosmetologist.data.source.local.db.AppDatabase
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentDao
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.local.db.dao.ClientDao
import com.hfad.mycosmetologist.data.source.local.db.dao.ServiceDao
import com.hfad.mycosmetologist.data.source.local.db.dao.WorkerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,

            "my_cosmetologist.db"
        )
            .build()
    }

    @Provides
    fun provideAppointmentDao(
        database: AppDatabase
    ): AppointmentDao{
        return database.appointmentDao()
    }

    @Provides
    fun provideAppointmentServiceDao(
        database: AppDatabase
    ): AppointmentServiceDao{
        return database.appointmentServiceDao()
    }

    @Provides
    fun provideClientDao(
        database: AppDatabase
    ): ClientDao {
        return database.clientDao()
    }

    @Provides
    fun provideServiceDao(
        database: AppDatabase
    ): ServiceDao{
        return database.serviceDao()
    }

    @Provides
    fun provideWorkerDao(
        database: AppDatabase
    ): WorkerDao{
        return database.workerDao()
    }

}
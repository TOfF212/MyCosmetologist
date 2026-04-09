package com.hfad.mycosmetologist.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "my_cosmetologist.db",
            )
            .build()

    @Provides
    fun provideAppointmentDao(database: AppDatabase): AppointmentDao = database.appointmentDao()

    @Provides
    fun provideAppointmentServiceDao(database: AppDatabase): AppointmentServiceDao = database.appointmentServiceDao()

    @Provides
    fun provideClientDao(database: AppDatabase): ClientDao = database.clientDao()

    @Provides
    fun provideServiceDao(database: AppDatabase): ServiceDao = database.serviceDao()

    @Provides
    fun provideWorkerDao(database: AppDatabase): WorkerDao = database.workerDao()
}

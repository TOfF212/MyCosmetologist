package com.hfad.mycosmetologist.data.di

import com.hfad.mycosmetologist.data.repository.AppointmentRepositoryImpl
import com.hfad.mycosmetologist.data.repository.ClientRepositoryImpl
import com.hfad.mycosmetologist.data.repository.ServiceRepositoryImpl
import com.hfad.mycosmetologist.data.repository.WorkerRepositoryImpl
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentDao
import com.hfad.mycosmetologist.data.source.local.db.dao.AppointmentServiceDao
import com.hfad.mycosmetologist.data.source.local.db.dao.ClientDao
import com.hfad.mycosmetologist.data.source.local.db.dao.ServiceDao
import com.hfad.mycosmetologist.data.source.local.db.dao.WorkerDao
import com.hfad.mycosmetologist.domain.repository.AppointmentRepository
import com.hfad.mycosmetologist.domain.repository.ClientRepository
import com.hfad.mycosmetologist.domain.repository.ServiceRepository
import com.hfad.mycosmetologist.domain.repository.WorkerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAppointmentRepository(
        impl: AppointmentRepositoryImpl
    ): AppointmentRepository

    @Binds
    abstract fun bindClientRepository(
        impl: ClientRepositoryImpl,
    ): ClientRepository

    @Binds
    abstract fun bindServiceRepository(
        impl: ServiceRepositoryImpl
    ): ServiceRepository

    @Binds
    abstract fun bindWorkerRepository(
        impl: WorkerRepositoryImpl
    ): WorkerRepository
}
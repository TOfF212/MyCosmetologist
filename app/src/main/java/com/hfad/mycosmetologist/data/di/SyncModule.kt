package com.hfad.mycosmetologist.data.di

import com.hfad.mycosmetologist.data.repository.ServiceRepositoryImpl
import com.hfad.mycosmetologist.data.sync.service.ServiceSyncManager
import com.hfad.mycosmetologist.data.sync.client.ClientSyncManager
import com.hfad.mycosmetologist.data.sync.core.Syncable
import com.hfad.mycosmetologist.data.sync.worker.WorkerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {

    @Binds
    @IntoSet
    abstract fun bindWorkerSyncManager(
        impl: WorkerSyncManager,
    ): Syncable

    @Binds
    @IntoSet
    abstract fun bindClientSyncManager(
        impl: ClientSyncManager,
    ): Syncable

    @Binds
    @IntoSet
    abstract fun bindServiceSyncManager(
        impl: ServiceSyncManager,
    ): Syncable

    @Binds
    @IntoSet
    abstract fun bindAppointmentSyncManager(
        impl: AppointmentSyncManager,
    ): Syncable
}

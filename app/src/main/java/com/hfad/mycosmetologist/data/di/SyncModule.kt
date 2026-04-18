package com.hfad.mycosmetologist.data.di

import com.hfad.mycosmetologist.data.sync.core.Syncable
import com.hfad.mycosmetologist.data.sync.workers.WorkerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {

    @Binds
    abstract fun bindWorkerSyncManager(
        impl: WorkerSyncManager
    ): Syncable
}

package com.hfad.mycosmetologist.presentation.di

import androidx.navigation3.runtime.entryProvider
import androidx.room.ProvidedAutoMigrationSpec
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import com.hfad.mycosmetologist.domain.useCase.worker.IsWorkerAuthorized
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.EntryProviderInstaller
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import dagger.multibindings.IntoSet
import androidx.navigation3.runtime.EntryDsl

@Module
@InstallIn(ActivityRetainedComponent::class)
object NavigationModule {

    @Provides
    fun provideNavigator(): Navigator{
        return Navigator(AppScreen.Splash)
    }

    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller  = {

        entry<AppScreen.Splash> {
            //TODO
        }

        entry<AppScreen.Auth> {
            //TODO

        }

        entry<AppScreen.Home> {
            //TODO

        }
    }

}
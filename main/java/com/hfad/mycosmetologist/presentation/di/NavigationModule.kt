package com.hfad.mycosmetologist.presentation.di

import android.util.Log
import androidx.navigation3.runtime.entryProvider
import androidx.room.ProvidedAutoMigrationSpec
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import com.hfad.mycosmetologist.domain.useCase.worker.IsWorkerAuthorized
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator

import dagger.multibindings.IntoSet
import androidx.navigation3.runtime.EntryDsl
import com.hfad.mycosmetologist.presentation.main.auth.AuthScreen
import com.hfad.mycosmetologist.presentation.main.home.HomeScreen
import com.hfad.mycosmetologist.presentation.main.splash.SplashScreen

@Module
@InstallIn(ActivityRetainedComponent::class)
object NavigationModule {

    @Provides
    @ActivityRetainedScoped
    fun provideNavigator(): Navigator{
        return Navigator()
    }
}
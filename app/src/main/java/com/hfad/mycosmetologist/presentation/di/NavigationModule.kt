package com.hfad.mycosmetologist.presentation.di

import com.hfad.mycosmetologist.presentation.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object NavigationModule {
    @Provides
    @ActivityRetainedScoped
    fun provideNavigator(): Navigator = Navigator()
}

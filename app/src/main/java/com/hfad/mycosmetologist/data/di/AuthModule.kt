package com.hfad.mycosmetologist.data.di



import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.hfad.mycosmetologist.data.repository.AuthRepositoryImpl
import com.hfad.mycosmetologist.data.source.local.auth.AuthLocalStorage
import com.hfad.mycosmetologist.data.source.remote.auth.FirebaseAuthSource
import com.hfad.mycosmetologist.domain.repository.AuthRepository
import com.hfad.mycosmetologist.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {



    @Provides
    fun provideAuthLocalStorage(prefs: SharedPreferences) =
        AuthLocalStorage(prefs)

}

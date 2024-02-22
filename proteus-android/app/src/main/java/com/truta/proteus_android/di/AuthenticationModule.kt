package com.truta.proteus_android.di

import com.truta.proteus_android.domain.service.AuthenticationService
import com.truta.proteus_android.domain.service.IAuthenticationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationService(): IAuthenticationService {
        return AuthenticationService()
    }
}
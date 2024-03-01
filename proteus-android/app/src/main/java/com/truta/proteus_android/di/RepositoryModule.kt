package com.truta.proteus_android.di

import com.truta.proteus_android.repository.ScheduleRepository
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.service.MappingService
import com.truta.proteus_android.service.StorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
     fun provideScheduleRepository(
        authService: AuthenticationService,
        mappingService: MappingService
     ): ScheduleRepository {
        return ScheduleRepository(authService, mappingService)
     }
}
package com.truta.proteus_android.di

import com.truta.proteus_android.repository.ScheduleRepository
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
     fun provideScheduleRepository(): ScheduleRepository {
        return ScheduleRepository()
     }
}
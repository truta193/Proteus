package com.truta.proteus_android.di

import com.truta.proteus_android.service.IMappingService
import com.truta.proteus_android.service.MappingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappingModule {

    @Provides
    @Singleton
    fun provideMappingService(): IMappingService {
        return MappingService()
    }
}
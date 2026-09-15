package com.example.assignment2.di

import com.example.assignment2.data.repository.AnimalRepository
import com.example.assignment2.data.repository.AnimalRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAnimalRepository(
        animalRepository: AnimalRepository
    ): AnimalRepositoryInterface
}
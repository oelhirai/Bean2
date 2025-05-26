package com.example.bean2.di

import com.example.bean2.data.repository.CoffeeRepository
import com.example.bean2.data.repository.CoffeeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindCoffeeRepository(repository: CoffeeRepositoryImpl): CoffeeRepository
}

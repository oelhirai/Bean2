package com.example.bean2.di

import android.content.Context
import com.example.bean2.data.local.AppDatabase
import com.example.bean2.data.local.dao.CoffeeBagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideCoffeeBagDao(database: AppDatabase): CoffeeBagDao {
        return database.coffeeBagDao()
    }
}

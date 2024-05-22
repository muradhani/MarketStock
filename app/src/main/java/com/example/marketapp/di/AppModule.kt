package com.example.marketapp.di

import android.content.Context
import androidx.room.Room
import com.example.marketapp.data.local.dao.StockDao
import com.example.marketapp.data.local.database.StockDatabase
import com.example.marketapp.data.remote.dto.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStockApi():StockApi{
        return Retrofit.Builder().baseUrl(StockApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesStockDatabase(@ApplicationContext context:Context):StockDatabase{
        return Room.databaseBuilder(context,StockDatabase::class.java,"stock.db").build()
    }

    @Provides
    @Singleton
    fun provideDap(database: StockDatabase):StockDao{
        return database.dao()
    }
}
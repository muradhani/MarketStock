package com.example.marketapp.di

import com.example.marketapp.data.csv.CompanyListingsParser
import com.example.marketapp.data.csv.CsvParser
import com.example.marketapp.data.csv.IntradayInfoParser
import com.example.marketapp.data.repo.StockRepoImpl
import com.example.marketapp.domian.model.CompanyListing
import com.example.marketapp.domian.model.IntradayInfo
import com.example.marketapp.domian.repo.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ):CsvParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepoImpl: StockRepoImpl
    ):StockRepository

    @Binds
    @Singleton
    abstract fun bindIntraDayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ):CsvParser<IntradayInfo>

}
package com.example.marketapp.domian.repo

import com.example.marketapp.domian.model.CompanyInfo
import com.example.marketapp.domian.model.CompanyListing
import com.example.marketapp.domian.model.IntradayInfo
import com.example.marketapp.domian.model.State
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(query:String): Flow<State<List<CompanyListing>>>
    suspend fun getIntradayInfo(symbol: String): State<List<IntradayInfo>>
    suspend fun getCompanyInfo(symbol: String): State<CompanyInfo>
}
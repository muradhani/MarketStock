package com.example.marketapp.data.repo

import com.example.marketapp.data.csv.CompanyListingsParser
import com.example.marketapp.data.csv.CsvParser
import com.example.marketapp.data.csv.IntradayInfoParser
import com.example.marketapp.data.local.dao.StockDao
import com.example.marketapp.data.local.database.StockDatabase
import com.example.marketapp.data.mappers.toCompanyInfo
import com.example.marketapp.data.mappers.toCompanyListing
import com.example.marketapp.data.mappers.toCompanyListingEntitiy
import com.example.marketapp.data.remote.dto.StockApi
import com.example.marketapp.domian.model.CompanyInfo
import com.example.marketapp.domian.model.CompanyListing
import com.example.marketapp.domian.model.IntradayInfo
import com.example.marketapp.domian.model.State
import com.example.marketapp.domian.repo.StockRepository
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.io.InputStreamReader

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepoImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao,
    private val parser : CompanyListingsParser,
    private val intradayInfoParser: CsvParser<IntradayInfo>
) : StockRepository {
    override suspend fun getCompanyListings(query: String): Flow<State<List<CompanyListing>>> {
        return flow {
            emit(State.Loading)
            val localListings = dao.searchCompanyListing(query)
            if (localListings.isEmpty()) {
                try {
                    val result = api.getListings()
                    val remoteList = parser.parse(result.byteStream())
                    dao.clearCompanyListings()
                    dao.intertCompanyListings(remoteList.map { it.toCompanyListingEntitiy() })
                    emit(State.Success(remoteList))
                } catch (e: Exception) {
                    emit(State.Error(e.message.toString()))
                }
            }else{
                emit(State.Success(localListings.map { it.toCompanyListing() }))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): State<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            State.Success(result)
        }catch (e:IOException){
            State.Error(e.message)
        }
    }

    override suspend fun getCompanyInfo(symbol: String): State<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            State.Success(result.toCompanyInfo())
        }catch (e:IOException){
            State.Error(e.message)
        }
    }
}
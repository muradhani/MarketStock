package com.example.marketapp.data.remote.dto

import com.example.marketapp.data.local.CompanyListingEntitiy
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    companion object{
        const val API_KEY = "MXLK01RFM9DVFEA2"
        const val BASE_URL = "https://www.alphavantage.co/"
    }

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query("apikey")apiKey:String = API_KEY): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol")symbol: String,
        @Query("apiKey")apiKey: String = API_KEY
    ): ResponseBody

    @GET("query?funtion=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol")symbol: String,
        @Query("apiKey")apiKey: String = API_KEY
    ): CompanyInfoDto
}
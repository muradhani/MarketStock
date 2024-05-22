package com.example.marketapp.domian.model

import com.squareup.moshi.Json

data class CompanyInfo(
    val symbol: String,
    val description: String,
    val companyName: String,
    val country: String,
    val industry: String,
)

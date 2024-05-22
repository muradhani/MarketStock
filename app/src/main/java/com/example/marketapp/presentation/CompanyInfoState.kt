package com.example.marketapp.presentation

import com.example.marketapp.domian.model.CompanyInfo
import com.example.marketapp.domian.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo:List<IntradayInfo>? = null,
    val company : CompanyInfo? = null,
    val isLoading : Boolean = false,
    val error : String = ""
)

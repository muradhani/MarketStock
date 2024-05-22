package com.example.marketapp.presentation.companyListings

import com.example.marketapp.domian.model.CompanyListing

data class CompanyListingsState(
    val companies : List<CompanyListing> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false ,
    val searchQuery : String = ""
)

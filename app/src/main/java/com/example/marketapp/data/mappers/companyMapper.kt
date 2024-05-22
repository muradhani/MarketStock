package com.example.marketapp.data.mappers

import com.example.marketapp.data.local.CompanyListingEntitiy
import com.example.marketapp.data.remote.dto.CompanyInfoDto
import com.example.marketapp.domian.model.CompanyInfo
import com.example.marketapp.domian.model.CompanyListing

fun CompanyListingEntitiy.toCompanyListing():CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntitiy():CompanyListingEntitiy{
    return CompanyListingEntitiy(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}
fun CompanyInfoDto.toCompanyInfo():CompanyInfo{
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        companyName = companyName ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}
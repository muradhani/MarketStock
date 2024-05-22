package com.example.marketapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marketapp.data.local.CompanyListingEntitiy

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun intertCompanyListings(companyListingEntities: List<CompanyListingEntitiy>)

    @Query("DELETE  FROM companylistingentitiy")
    suspend fun clearCompanyListings()

    @Query("SELECT * FROM companylistingentitiy WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR UPPER(:query) == symbol ")
    suspend fun searchCompanyListing(query: String):List<CompanyListingEntitiy>
}
package com.example.marketapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marketapp.data.local.CompanyListingEntitiy
import com.example.marketapp.data.local.dao.StockDao

@Database(version = 1 , entities = [CompanyListingEntitiy::class], exportSchema = false)
abstract class StockDatabase:RoomDatabase() {
    abstract fun dao() : StockDao
}
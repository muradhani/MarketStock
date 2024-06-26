package com.example.marketapp.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.marketapp.data.mappers.toIntradayInfo
import com.example.marketapp.data.remote.dto.IntradayInfoDto
import com.example.marketapp.domian.model.CompanyListing
import com.example.marketapp.domian.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class IntradayInfoParser @Inject constructor():CsvParser<IntradayInfo> {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader.readAll().drop(1).mapNotNull {line ->
                val timeStamp = line.getOrNull(0)?: return@mapNotNull null
                val close = line.getOrNull(4)?: return@mapNotNull null
                val dto = IntradayInfoDto(timeStamp, close.toDouble())
                dto.toIntradayInfo()
            }.filter {
                it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth

            }.sortedBy {
                it.date.hour
            }.also {
                csvReader.close()
            }
        }
    }
}
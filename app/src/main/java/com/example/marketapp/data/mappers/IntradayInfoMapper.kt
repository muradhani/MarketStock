package com.example.marketapp.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi

import com.example.marketapp.data.remote.dto.IntradayInfoDto
import com.example.marketapp.domian.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp,formatter)
    return IntradayInfo(
        localDateTime,
        close
    )

}
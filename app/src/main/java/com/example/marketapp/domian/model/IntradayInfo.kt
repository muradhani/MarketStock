package com.example.marketapp.domian.model

import java.time.LocalDateTime

data class IntradayInfo(
    val date: LocalDateTime,
    val close:Double?
)

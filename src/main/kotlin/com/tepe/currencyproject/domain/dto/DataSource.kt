package com.tepe.currencyproject.domain.dto

data class DataSource (
    val source: String,
    val response: List<CurrencyResponse>?,
    val success: Boolean
)
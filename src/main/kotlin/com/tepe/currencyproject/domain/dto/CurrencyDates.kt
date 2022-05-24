package com.tepe.currencyproject.domain.dto

data class CurrencyDates(
    val lowerDate: String,
    val higherDate: String,
    val base: String,
    val currencies: List<String>
)

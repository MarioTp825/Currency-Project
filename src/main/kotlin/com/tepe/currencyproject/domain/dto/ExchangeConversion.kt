package com.tepe.currencyproject.domain.dto

data class ExchangeConversion(
    val base: String,
    val date: String,
    val historical: Boolean,
    val motd: Motd,
    val rates: Rates?,
    val success: Boolean
)
package com.tepe.currencyproject.domain.dto

data class CurrencyHolder(
    val date: String,
    val base: String,
    val currencies: List<String>
)

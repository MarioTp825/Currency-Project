package com.tepe.currencyproject.domain.repository

import com.tepe.currencyproject.domain.dto.CurrencyResponse

interface CurrencyService {

    fun converter(date: String, currency: List<String>, base: String): List<CurrencyResponse>?

    val name: String
}
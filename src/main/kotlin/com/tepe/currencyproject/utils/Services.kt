package com.tepe.currencyproject.utils

import com.tepe.currencyproject.persistance.ApiLayer
import com.tepe.currencyproject.persistance.CurrencyApi
import com.tepe.currencyproject.persistance.ExchangeRate

object Services {
    val WEB_SERVICES = listOf(
        CurrencyApi(),
        ApiLayer(),
        ExchangeRate(),
    )
}
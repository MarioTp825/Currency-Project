package com.tepe.currencyproject.domain.dto

sealed class CurrencyApiConversion {
    data class CurrencyAPI(
        val data: Data?,
        val meta: Meta?
    )

    data class Data(
        val EUR: EUR?,
        val USD: USD?,
        val GBP: GBP?,
        val JPY: JPY?,
    )

    data class Meta(
        val last_updated_at: String
    )

    data class EUR(
        val code: String,
        val value: Double
    )

    data class JPY(
        val code: String,
        val value: Double
    )

    data class GBP(
        val code: String,
        val value: Double
    )

    data class USD(
        val code: String,
        val value: Double
    )
}
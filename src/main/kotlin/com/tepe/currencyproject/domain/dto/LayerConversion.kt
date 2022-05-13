package com.tepe.currencyproject.domain.dto


sealed class LayerAPI {
    data class APILayer(
        val base: String?,
        val date: String?,
        val historical: Boolean?,
        val rates: Rates?,
        val success: Boolean?,
        val timestamp: Int?
    )

    data class Rates(
        val EUR: Float?,
        val GBP: Float?,
        val USD: Float?,
        val JPY: Float?
    )
}
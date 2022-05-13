package com.tepe.currencyproject.persistance

import com.tepe.currencyproject.domain.dto.CurrencyResponse
import com.tepe.currencyproject.domain.dto.ExchangeConversion
import com.tepe.currencyproject.domain.dto.LayerAPI
import com.tepe.currencyproject.domain.repository.CurrencyService
import com.tepe.currencyproject.utils.HttpRoutes
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

class ExchangeRate: CurrencyService {
    private val temp = RestTemplate()
    private val headers = HttpHeaders()

    override fun converter(date: String, currency: List<String>): List<CurrencyResponse>? {
        val url = "${HttpRoutes.EXCHANGE_BASE_URL}$date?symbols=${currency.joinToString(",")}"

        val res = temp.exchange<ExchangeConversion>(
            url = url,
            method = HttpMethod.GET,
            HttpEntity<String>(headers),
            LayerAPI.APILayer::class.java
        )

        if (res.body == null || res.body!!.rates == null) return null
        val answer = mutableListOf<CurrencyResponse>()
        res.body!!.rates!!.let { rt ->
            rt.EUR.let { answer.add(
                currency("EUR", it)
            ) }
            rt.GBP.let { answer.add(
                currency("GBP", it)
            ) }
            rt.JPY.let { answer.add(
                currency("JPY", it)
            ) }
            rt.USD.let { answer.add(
                currency("USD", it)
            ) }
        }

        return if (res.statusCode == HttpStatus.OK && res.body?.success == true) answer.toList() else null
    }

    override val name: String
        get() = "ExchangeRate"

    private fun currency(key: String?, value: Double?) = CurrencyResponse(key, value)
}

package com.tepe.currencyproject.persistance

import com.tepe.currencyproject.domain.dto.CurrencyResponse
import com.tepe.currencyproject.domain.dto.LayerAPI.APILayer
import com.tepe.currencyproject.domain.repository.CurrencyService
import com.tepe.currencyproject.utils.HttpRoutes
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange


class ApiLayer : CurrencyService {
    private val temp = RestTemplate()
    private val headers = HttpHeaders()

    init {
        headers["apikey"] = HttpRoutes.API_LAYER_KEY
    }

    override fun converter(date: String, currency: List<String>, base: String): List<CurrencyResponse>? {
        val url = "${HttpRoutes.API_LAYER_BASE_URL}$date?base=$base&symbols=${currency.joinToString(",")}"

        val res = temp.exchange<APILayer>(
            url = url,
            method = HttpMethod.GET,
            HttpEntity<String>(headers),
            APILayer::class.java
        )

        if (res.body == null || res.body!!.rates == null) return null
        val answer = mutableListOf<CurrencyResponse>()
        res.body!!.rates!!.let { rt ->
            rt.EUR?.let { answer.add(
                currency("EUR", it.toDouble())
            ) }
            rt.GBP?.let { answer.add(
                currency("GBP", it.toDouble())
            ) }
            rt.JPY?.let { answer.add(
                currency("JPY", it.toDouble())
            ) }
            rt.USD?.let { answer.add(
                currency("USD", it.toDouble())
            ) }
        }

        return if (res.statusCode == HttpStatus.OK && res.body?.success == true) answer.toList() else null
    }

    override val name: String
        get() = "ApiLayer"

    private fun currency(key: String?, value: Double?) = CurrencyResponse(key, value)
}

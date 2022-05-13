package com.tepe.currencyproject.persistance

import com.tepe.currencyproject.domain.dto.CurrencyApiConversion.CurrencyAPI
import com.tepe.currencyproject.domain.dto.CurrencyResponse
import com.tepe.currencyproject.domain.repository.CurrencyService
import com.tepe.currencyproject.utils.HttpRoutes
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

class CurrencyApi : CurrencyService {
    private val temp = RestTemplate()
    private val headers = HttpHeaders()

    init {
        headers["apikey"] = HttpRoutes.CURRENCY_API_KEY
    }

    override fun converter(date: String, currency: List<String>): List<CurrencyResponse>? {
        val url = "${HttpRoutes.CURRENCY_API_BASE_URL}?date=$date&currencies=${currency.joinToString(",")}"

        val res = temp.exchange<CurrencyAPI>(
            url = url,
            method = HttpMethod.GET,
            HttpEntity<String>(headers),
            CurrencyAPI::class.java
        )

        if (res.body == null || res.body!!.data == null) return null
        val answer = mutableListOf<CurrencyResponse>()
        res.body!!.data!!.let { rt ->
            rt.EUR?.let { answer.add(
                currency("EUR", it.value)
            ) }
            rt.GBP?.let { answer.add(
                currency("GBP", it.value)
            ) }
            rt.JPY?.let { answer.add(
                currency("JPY", it.value)
            ) }
            rt.USD?.let { answer.add(
                currency("USD", it.value)
            ) }
        }

        return if (res.statusCode == HttpStatus.OK) answer.toList() else null
    }

    override val name: String
        get() = "CurrencyApi"

    private fun currency(key: String?, value: Double?) = CurrencyResponse(key, value)

}
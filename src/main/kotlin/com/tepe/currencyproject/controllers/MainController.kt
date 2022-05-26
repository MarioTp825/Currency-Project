package com.tepe.currencyproject.controllers

import com.tepe.currencyproject.domain.dto.*
import com.tepe.currencyproject.utils.Services
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
//Mario Tepe
@RestController
@RequestMapping("/currency")
class MainController {
    val req = Services.WEB_SERVICES

    @PostMapping("/all")
    fun all(@RequestBody currency: CurrencyHolder): Response {
        val ans = mutableListOf<DataSource>()
        req.forEach {
            val list = it.converter(date = currency.date, currency = currency.currencies, base = currency.base)
            ans.add(
                DataSource(
                    source = it.name,
                    response = list,
                    success = list != null
                )
            )
        }

        return Response(
            date = currency.date,
            sources = ans.toList(),
            null
        )
    }

    @PostMapping("/indices")
    fun indices(@RequestBody currency: CurrencyDates): Response {
        val response = mutableListOf<DataSource>()
        val lower = mutableMapOf<String, DataSource>()
        val higher = mutableMapOf<String, DataSource>()
        req.forEach {
            val low = it.converter(date = currency.lowerDate, currency = currency.currencies, base = currency.base)
            val high = it.converter(date = currency.higherDate, currency = currency.currencies, base = currency.base)
            lower[it.name] = DataSource(
                source = it.name,
                response = low,
                success = low != null
            )

            higher[it.name] = DataSource(
                source = it.name,
                response = high,
                success = high != null
            )
        }

        for (first in lower) {
            val second = higher[first.key]!!
            val rs = mutableListOf<CurrencyResponse>()

            for (curr in first.value.response!!) {
                val cr = second.response!!.first { curr.currency == it.currency }.rates!!
                rs.add(
                    CurrencyResponse(
                        currency = curr.currency,
                        rates = curr.rates!! / cr,
                    )
                )
            }

            response.add(
                DataSource(
                    source = first.key,
                    response = rs.toList(),
                    success = first.value.success && second.success
                )
            )
        }

        return Response(
            date = "${currency.lowerDate} - ${currency.higherDate}",
            sources = response.toList(),
            null
        )
    }

    @PostMapping("/{service}")
    fun singleService(@PathVariable service: String, @RequestBody currency: CurrencyHolder): Response {
        val ws = req.find { it.name.lowercase() == service.lowercase() }
        ws?.let {
            val list = it.converter(
                date = currency.date,
                currency = currency.currencies,
                base = currency.base
            )

            return Response(
                date = currency.date,
                sources = listOf(
                    DataSource(
                        source = it.name,
                        response = list,
                        success = list != null
                    )
                ),
                null
            )
        }

        return Response(
            date = currency.date,
            sources = listOf(),
            msg = "No such service registered. You can use de following options: [all, ${req.joinToString { it.name }}]"
        )

    }


}
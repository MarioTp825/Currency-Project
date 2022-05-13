package com.tepe.currencyproject.controllers

import com.tepe.currencyproject.domain.dto.CurrencyHolder
import com.tepe.currencyproject.domain.dto.DataSource
import com.tepe.currencyproject.domain.dto.Response
import com.tepe.currencyproject.utils.Services
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/currency")
class MainController {
    val req = Services.WEB_SERVICES

    @PostMapping("/all")
    fun all(@RequestBody currency: CurrencyHolder): Response {
        val ans = mutableListOf<DataSource>()
        req.forEach {
            val list = it.converter(date = currency.date ,currency  = currency.currencies)
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

    @PostMapping("/{service}")
    fun singleService(@PathVariable service: String, @RequestBody currency: CurrencyHolder): Response {
        val ws = req.find { it.name.lowercase() == service.lowercase() }
        ws?.let {
            val list = it.converter(
                date = currency.date,
                currency = currency.currencies,
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
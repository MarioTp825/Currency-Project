package com.tepe.currencyproject.domain.dto

data class Response (
    val date: String?,
    val sources: List<DataSource>,
    val msg: String?
)
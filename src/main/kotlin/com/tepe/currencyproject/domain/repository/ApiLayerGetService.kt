package com.tepe.currencyproject.domain.repository

import com.tepe.currencyproject.domain.dto.LayerAPI

interface ApiLayerGetService {

    fun convert(from: String, to: String, amount: Float): LayerAPI.APILayer?

}
package com.tepe.currencyproject.utils

import com.google.gson.Gson

object Converter {
    private var gson: Gson? = null

    fun getInstance(): Gson {
        return if(gson == null) {
            gson = Gson()
            gson!!
        } else gson!!
    }
}
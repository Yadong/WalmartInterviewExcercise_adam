package com.walmartinterviewexercise.model

import com.walmartinterviewexercise.api.APIService

class CountryManager(private val apiService: APIService) {
    suspend fun getCountryList() = apiService.getCountryList()
}

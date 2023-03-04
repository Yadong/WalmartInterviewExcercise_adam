package com.walmartinterviewexercise.api

import com.walmartinterviewexercise.model.Country
import retrofit2.http.GET

interface APIService {

    @GET("countries.json")
    suspend fun getCountryList(): List<Country>
}

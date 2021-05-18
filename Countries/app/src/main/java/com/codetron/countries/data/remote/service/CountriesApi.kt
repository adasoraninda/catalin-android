package com.codetron.countries.data.remote.service

import com.codetron.countries.data.remote.response.CountryResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {

    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<CountryResponse>>

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }

}
package com.codetron.countries.data.remote.service

import com.codetron.countries.data.remote.response.CountryResponse
import io.reactivex.Single
import javax.inject.Inject

class CountryService @Inject constructor(
    private val apiService: CountriesApi
) {

    fun fetchCountries(): Single<List<CountryResponse>> {
        return apiService.getCountries()
    }

}
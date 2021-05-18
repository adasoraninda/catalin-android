package com.codetron.countries.data.model

import com.codetron.countries.data.remote.response.CountryResponse

data class Country(
    val name: String,
    val capital: String,
    val image: String,
) {

    companion object {
        fun getDummyData(): List<Country> {
            val countries = arrayListOf<Country>()
            repeat(20) {
                countries.add(
                    Country(
                        name = "Country ${it + 1}",
                        capital = "$it",
                        image = ""
                    )
                )
            }
            return countries
        }

        fun fromResponse(response: CountryResponse): Country {
            return Country(
                name = response.countryName ?: "",
                image = response.flag ?: "",
                capital = response.capital ?: ""
            )
        }

    }

}
package com.codetron.countries.data.remote.response

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("name")
    val countryName: String? = null,
    @SerializedName("capital")
    val capital: String? = null,
    @SerializedName("flagPNG")
    val flag: String? = null
)
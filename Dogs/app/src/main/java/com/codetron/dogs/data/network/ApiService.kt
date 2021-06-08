package com.codetron.dogs.data.network

import com.codetron.dogs.data.Dog
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {

    companion object{
        const val BASE_URL = "https://raw.githubusercontent.com"
    }

    @GET("/DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<Dog>>

}
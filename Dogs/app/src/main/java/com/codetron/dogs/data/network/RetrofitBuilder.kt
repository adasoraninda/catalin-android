package com.codetron.dogs.data.network

import com.codetron.dogs.data.network.ApiService.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@ExperimentalSerializationApi
class RetrofitBuilder private constructor() {

    private fun buildClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun buildRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .client(buildClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory(contentType))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun buildService(): ApiService {
        return buildRetrofit().create(ApiService::class.java)
    }

    companion object {
        @Volatile
        private var INSTANCE: ApiService? = null
        private val LOCK = Any()

        fun getInstanceService(): ApiService {
            return INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: RetrofitBuilder().buildService().also {
                    INSTANCE = it
                }
            }
        }

    }

}
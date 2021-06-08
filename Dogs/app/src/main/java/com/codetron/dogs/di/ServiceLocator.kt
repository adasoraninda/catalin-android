package com.codetron.dogs.di

import android.content.Context
import com.codetron.dogs.data.DogRepository
import com.codetron.dogs.data.DogRepositoryImpl
import kotlinx.serialization.ExperimentalSerializationApi

object ServiceLocator {

    @ExperimentalSerializationApi
    fun provideRepository(context: Context): DogRepository {
        return DogRepositoryImpl.getInstance(context)
    }

}
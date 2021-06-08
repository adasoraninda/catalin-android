package com.codetron.dogs.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codetron.dogs.data.DogRepository
import com.codetron.dogs.di.ServiceLocator
import com.codetron.dogs.ui.detail.DetailViewModel
import com.codetron.dogs.ui.list.ListViewModel
import kotlinx.serialization.ExperimentalSerializationApi


class ViewModelFactory private constructor(
    private val repository: DogRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java)->{
                DetailViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown view model class: ${modelClass.simpleName}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        private val LOCK = Any()

        @ExperimentalSerializationApi
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: ViewModelFactory(ServiceLocator.provideRepository(context)).also {
                    INSTANCE = it
                }
            }
        }
    }
}
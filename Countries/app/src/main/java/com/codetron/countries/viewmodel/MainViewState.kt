package com.codetron.countries.viewmodel

sealed class MainViewState<out T> {
    object Loading : MainViewState<Nothing>()
    data class Success<out T>(val data: T) : MainViewState<T>()
    data class Error(val message: String) : MainViewState<Nothing>()
}
package com.codetron.countries.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codetron.countries.data.remote.service.CountryService
import com.codetron.countries.viewmodel.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val service: CountryService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(service) as T
    }

}
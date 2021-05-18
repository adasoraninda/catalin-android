package com.codetron.countries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codetron.countries.data.model.Country
import com.codetron.countries.data.remote.response.CountryResponse
import com.codetron.countries.data.remote.service.CountryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val countryService: CountryService) : ViewModel() {

    private val _viewStateCountry = MutableLiveData<MainViewState<List<Country>>>()
    val viewStateCountry: LiveData<MainViewState<List<Country>>>
        get() = _viewStateCountry

    private val compositeDisposable = CompositeDisposable()

    init {
        fetchCountries()
    }

    fun fetchCountries() {
        _viewStateCountry.value = MainViewState.Loading


        compositeDisposable.add(
            countryService.fetchCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountryResponse>>() {
                    override fun onSuccess(t: List<CountryResponse>) {
                        _viewStateCountry.value = MainViewState.Success(
                            t.map { Country.fromResponse(it) }
                        )
                    }

                    override fun onError(e: Throwable) {
                        _viewStateCountry.value = MainViewState.Error(e.message.toString())
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
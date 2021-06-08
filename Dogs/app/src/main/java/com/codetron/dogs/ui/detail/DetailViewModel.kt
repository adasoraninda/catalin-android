package com.codetron.dogs.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codetron.dogs.data.Dog
import com.codetron.dogs.data.DogRepository
import com.codetron.dogs.ui.ViewState
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: DogRepository
) : ViewModel() {

    private val _dog = MutableLiveData<ViewState<Dog?>>()
    val dog: LiveData<ViewState<Dog?>> get() = _dog

    fun getDog(id: Int?) {
        viewModelScope.launch {
            id?.let {
                repository.getDog(id) { state ->
                    _dog.value = state
                }
            }
        }
    }
}
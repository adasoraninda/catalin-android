package com.codetron.dogs.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codetron.dogs.data.Dog
import com.codetron.dogs.data.DogRepository
import com.codetron.dogs.ui.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class ListViewModel(private val repository: DogRepository) : ViewModel() {

    sealed class Event {
        data class EventDelete(val message: String?) : Event()
    }

    private val channelFlow = Channel<Event>(Channel.BUFFERED)
    val eventFlow = channelFlow.receiveAsFlow()

    private val dataSources = MediatorLiveData<ViewState<List<Dog>>>()

    val result: LiveData<ViewState<List<Dog>>> get() = dataSources

    init {
        fetchData()
    }

    fun fetchData() {
        dataSources.addSource(repository.getAllDogs()) {
            dataSources.value = it
        }
    }

    fun deleteAllData() {
        repository.deleteAllDogs { message ->
            viewModelScope.launch {
                channelFlow.send(Event.EventDelete(message))
            }
        }
    }

}
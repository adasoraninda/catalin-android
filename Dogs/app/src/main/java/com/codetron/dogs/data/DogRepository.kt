package com.codetron.dogs.data

import androidx.lifecycle.LiveData
import com.codetron.dogs.ui.ViewState

interface DogRepository {

    fun getAllDogs(): LiveData<ViewState<List<Dog>>>

    suspend fun getDog(dogId: Int, Result: (ViewState<Dog?>) -> Unit)

    fun deleteAllDogs(message:(String?)->Unit)

}
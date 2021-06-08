package com.codetron.dogs.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codetron.dogs.data.local.DogDatabase
import com.codetron.dogs.data.network.ApiResponse
import com.codetron.dogs.data.network.RetrofitBuilder
import com.codetron.dogs.offlinecache.AppExecutors
import com.codetron.dogs.offlinecache.NetworkBoundResource
import com.codetron.dogs.ui.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
class DogRepositoryImpl private constructor(private val context: Context) : DogRepository {

    private val disposable by lazy { CompositeDisposable() }

    private val apiService by lazy { RetrofitBuilder.getInstanceService() }
    private val dogDao by lazy { DogDatabase.invoke(context).dogDao }
    private val appExecutors by lazy { AppExecutors() }

    override fun getAllDogs(): LiveData<ViewState<List<Dog>>> {
        return object : NetworkBoundResource<List<Dog>, List<Dog>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Dog>> {
                return dogDao.getAllDogs()
            }

            override fun shouldFetch(data: List<Dog>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<Dog>>> {
                return getAllDogsFromNetwork()
            }

            override fun saveCallResult(data: List<Dog>) {
                dogDao.insertAll(data)
            }
        }.asLiveData()
    }

    private fun getAllDogsFromNetwork(): LiveData<ApiResponse<List<Dog>>> {
        val apiResponse = MutableLiveData<ApiResponse<List<Dog>>>()

        val allDogsDisposable = apiService
            .getDogs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { disposable.clear() }
            .subscribeWith(object : DisposableSingleObserver<List<Dog>>() {
                override fun onSuccess(data: List<Dog>) {
                    apiResponse.value = ApiResponse.success(data)
                }

                override fun onError(error: Throwable) {
                    apiResponse.value = ApiResponse.error(error)
                }
            })

        disposable.add(allDogsDisposable)

        return apiResponse
    }

    override suspend fun getDog(dogId: Int, Result: (ViewState<Dog?>) -> Unit) {
        Result(ViewState.Loading)

        val dog = dogDao.getDog(dogId)

        if (dog == null) {
            Result(ViewState.Error(NullPointerException("No data!")))
            return
        }

        Result(ViewState.Success(dog))
    }

    override fun deleteAllDogs(message: (String?) -> Unit) {
        val deleteDogsDisposable = dogDao.deleteAllDogs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { disposable.clear() }
            .subscribe({
                message(null)
            }, { error ->
                message(error.message)
            })

        disposable.add(deleteDogsDisposable)
    }


    companion object {
        @Volatile
        private var INSTANCE: DogRepository? = null
        private val LOCK = Any()

        fun getInstance(context: Context): DogRepository {
            return INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: DogRepositoryImpl(context).also {
                    INSTANCE = it
                }
            }
        }
    }

}
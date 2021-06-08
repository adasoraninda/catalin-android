package com.codetron.dogs.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codetron.dogs.data.Dog
import io.reactivex.Completable

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dogs: List<Dog>)

    @Query("SELECT * FROM dog_table")
    fun getAllDogs(): LiveData<List<Dog>>

    @Query("SELECT * FROM dog_table WHERE id = :dogId")
    suspend fun getDog(dogId: Int): Dog?

    @Query("DELETE FROM dog_table")
    fun deleteAllDogs(): Completable

}
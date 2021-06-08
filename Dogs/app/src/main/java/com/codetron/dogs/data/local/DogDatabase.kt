package com.codetron.dogs.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codetron.dogs.data.Dog

@Database(entities = [Dog::class], version = 1)
abstract class DogDatabase : RoomDatabase() {
    abstract val dogDao: DogDao

    companion object {
        @Volatile
        private var INSTANCE: DogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): DogDatabase {
            return INSTANCE ?: synchronized(LOCK) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): DogDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                DogDatabase::class.java,
                "dog_database.db"
            ).build()
        }

    }

}
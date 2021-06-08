package com.codetron.dogs.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "dog_table")
@Serializable
data class Dog(
    @PrimaryKey
    @SerialName("id")
    val id: Int? = null,

    @ColumnInfo(name = "name")
    @SerialName("name")
    val name: String? = null,

    @ColumnInfo(name = "life_span")
    @SerialName("life_span")
    val lifeSpan: String? = null,

    @ColumnInfo(name = "breed_group")
    @SerialName("breed_group")
    val breedGroup: String? = null,

    @ColumnInfo(name = "bred_for")
    @SerialName("bred_for")
    val bredFor: String? = null,

    @ColumnInfo(name = "temperament")
    @SerialName("temperament")
    val temperament: String? = null,

    @ColumnInfo(name = "url")
    @SerialName("url")
    val imageUrl: String? = null
)
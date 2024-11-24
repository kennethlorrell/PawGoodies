package com.deepdark.pawgoodies.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.Animal

@Dao
interface AnimalDao {
    @Insert
    suspend fun insertAnimal(animal: Animal): Long

    @Insert
    suspend fun insertAnimals(animals: List<Animal>)

    @Query("SELECT * FROM animals")
    suspend fun getAllAnimals(): List<Animal>

    @Query("SELECT * FROM animals WHERE id = :id")
    suspend fun getAnimalById(id: Int): Animal?

    @Query("SELECT * FROM animals WHERE name = :animalName")
    suspend fun getAnimalByName(animalName: String): Animal?
}
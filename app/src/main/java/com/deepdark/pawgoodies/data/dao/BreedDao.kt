package com.deepdark.pawgoodies.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.Breed

@Dao
interface BreedDao {
    @Insert
    suspend fun insertBreed(breeds: Breed): Long

    @Insert
    suspend fun insertBreeds(breeds: List<Breed>)

    @Query("SELECT * FROM breeds")
    suspend fun getAllBreeds(): List<Breed>

    @Query("SELECT * FROM breeds WHERE id = :id")
    suspend fun getBreedById(id: Int): Breed?

    @Query("SELECT * FROM breeds WHERE animalId = :animalId")
    suspend fun getBreedsByAnimal(animalId: Int): List<Breed>
}

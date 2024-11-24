package com.deepdark.pawgoodies.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.Pet

@Dao
interface PetDao {
    @Insert
    suspend fun insertPet(pet: Pet): Long

    @Insert
    suspend fun insertPets(pets: List<Pet>)

    @Query("SELECT * FROM pets WHERE userId = :userId")
    suspend fun getPetsByUserId(userId: Int): List<Pet>

    @Query("SELECT * FROM pets WHERE id = :id")
    suspend fun getPetById(id: Int): Pet?

    @Query("DELETE FROM pets WHERE id = :id")
    suspend fun deletePetById(id: Int)
}

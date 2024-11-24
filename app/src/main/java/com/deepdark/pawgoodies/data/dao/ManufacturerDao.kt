package com.deepdark.pawgoodies.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.Category
import com.deepdark.pawgoodies.data.entities.Manufacturer

@Dao
interface ManufacturerDao {
    @Insert
    suspend fun insertManufacturer(manufacturers: Manufacturer): Long

    @Insert
    suspend fun insertManufacturers(manufacturers: List<Manufacturer>)

    @Query("SELECT * FROM manufacturers")
    suspend fun getAllManufacturers(): List<Manufacturer>

    @Query("SELECT * FROM manufacturers WHERE id = :id")
    suspend fun getManufacturerById(id: Int): Manufacturer?

    @Query("SELECT * FROM manufacturers WHERE name = :manufacturerName")
    suspend fun getManufacturerByName(manufacturerName: String): Manufacturer?
}

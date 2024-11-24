package com.deepdark.pawgoodies.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.Category

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(categories: Category): Long

    @Insert
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM categories")
    fun getAllCategoriesLive(): LiveData<List<Category>>

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): Category?

    @Query("SELECT * FROM categories WHERE name = :categoryName")
    suspend fun getCategoryByName(categoryName: String): Category?
}
package com.deepdark.pawgoodies.data.repositories

import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getAllCategories(): Flow<List<Category>> = db.categoryDao().getAllCategoriesLive()
}

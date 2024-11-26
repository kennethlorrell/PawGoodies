package com.deepdark.pawgoodies.data.repositories

import androidx.lifecycle.LiveData
import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getAllCategoriesLive(): LiveData<List<Category>> = db.categoryDao().getAllCategoriesLive()
}

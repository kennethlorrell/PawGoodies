package com.deepdark.pawgoodies.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.Category
import com.deepdark.pawgoodies.data.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val db: AppDatabase
) : ViewModel() {
    val categories: LiveData<List<Category>> = db.categoryDao().getAllCategoriesLive()
    val products: LiveData<List<Product>> = db.productDao().getAllProductsLive()
}

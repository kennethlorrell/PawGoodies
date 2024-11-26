package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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

    fun getProductById(productId: Int): LiveData<Product?> {
        return db.productDao().getProductByIdLive(productId).asLiveData()
    }
}

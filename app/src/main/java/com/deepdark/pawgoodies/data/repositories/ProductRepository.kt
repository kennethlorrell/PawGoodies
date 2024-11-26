package com.deepdark.pawgoodies.data.repositories

import androidx.lifecycle.LiveData
import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getAllProductsLive(): LiveData<List<Product>> = db.productDao().getAllProductsLive()

    fun getProductByIdLive(
        productId: Int
    ): LiveData<Product?> = db.productDao().getProductByIdLive(productId)
}

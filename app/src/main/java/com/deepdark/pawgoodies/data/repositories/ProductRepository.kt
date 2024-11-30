package com.deepdark.pawgoodies.data.repositories

import androidx.lifecycle.LiveData
import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getAllProductsLive(): LiveData<List<ProductWithState>> = db.productDao().getAllProductsWithStateLive()

    fun getProductByIdLive(
        productId: Int
    ): LiveData<ProductWithState?> = db.productDao().getProductWithStateByIdLive(productId)
}

package com.deepdark.pawgoodies.data.repositories

import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getAllProductsWithState(
        userId: Int
    ): Flow<List<ProductWithState>> = db.productDao().getAllProductsWithState(userId)

    fun getProductWithStateById(
        userId: Int,
        productId: Int
    ): Flow<ProductWithState?> = db.productDao().getProductWithStateById(userId, productId)
}

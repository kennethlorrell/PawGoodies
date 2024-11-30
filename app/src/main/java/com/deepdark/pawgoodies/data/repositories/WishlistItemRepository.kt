package com.deepdark.pawgoodies.data.repositories

import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.WishlistItem
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistItemRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getWishlistItems(
        userId: Int
    ): Flow<List<ProductWithState>> = db.wishlistItemDao().getWishlistItems(userId)

    suspend fun toggleWishlistItem(userId: Int, productId: Int) {
        val exists = db.wishlistItemDao().isInWishlist(userId, productId)

        if (exists) {
            db.wishlistItemDao().removeFromWishlist(userId = userId, productId = productId)
        } else {
            db.wishlistItemDao().addToWishlist(
                WishlistItem(userId = userId, productId = productId)
            )
        }
    }
}

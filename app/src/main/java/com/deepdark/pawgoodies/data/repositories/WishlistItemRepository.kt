package com.deepdark.pawgoodies.data.repositories

import androidx.lifecycle.LiveData
import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.WishlistItem
import javax.inject.Inject

class WishlistItemRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getWishlistItemsLive(
        userId: Int
    ): LiveData<List<WishlistItem>> = db.wishlistDao().getWishlistItems(userId)

    suspend fun addToWishlist(
        wishlistItem: WishlistItem
    ) = db.wishlistDao().addToWishlist(wishlistItem)

    suspend fun removeFromWishlist(
        wishlistItem: WishlistItem
    ) = db.wishlistDao().removeFromWishlist(wishlistItem)
}

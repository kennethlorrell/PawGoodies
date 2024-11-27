package com.deepdark.pawgoodies.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.WishlistItem

@Dao
interface WishlistItemDao {
    @Query("SELECT * FROM wishlist_items WHERE userId = :userId")
    fun getWishlistItems(userId: Int): LiveData<List<WishlistItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(wishlistItem: WishlistItem)

    @Delete
    suspend fun removeFromWishlist(wishlistItem: WishlistItem)
}

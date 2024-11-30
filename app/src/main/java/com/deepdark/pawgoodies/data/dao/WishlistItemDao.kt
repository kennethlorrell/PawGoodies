package com.deepdark.pawgoodies.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.WishlistItem
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistItemDao {
    @Query("""
        SELECT 
          p.*,
          c.name AS categoryName,
          m.name AS manufacturerName,
          EXISTS (
            SELECT 1
            FROM cart_items ci
            WHERE ci.productId = p.id
          ) AS isInCart,
          COALESCE((
            SELECT cart_items.quantity
            FROM cart_items
            WHERE cart_items.productId = p.id), 0
          ) AS cartQuantity,
          1 AS isInWishlist
        FROM wishlist_items
        INNER JOIN products p ON wishlist_items.productId = p.id
        LEFT JOIN categories c ON p.categoryId = c.id
        LEFT JOIN manufacturers m ON p.manufacturerId = m.id
        WHERE userId = :userId
    """)
    fun getWishlistItems(userId: Int): Flow<List<ProductWithState>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(wishlistItem: WishlistItem)

    @Query("DELETE FROM wishlist_items WHERE userId = :userId AND productId = :productId")
    suspend fun removeFromWishlist(userId: Int, productId: Int)

    @Query("""
        SELECT COUNT(*) > 0
        FROM wishlist_items
        WHERE userId = :userId AND productId = :productId
    """)
    suspend fun isInWishlist(userId: Int, productId: Int): Boolean
}

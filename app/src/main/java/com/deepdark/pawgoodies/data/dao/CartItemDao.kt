package com.deepdark.pawgoodies.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.deepdark.pawgoodies.data.entities.CartItem
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Query("""
        SELECT 
          p.*,
          c.name AS categoryName,
          m.name AS manufacturerName,
          1 AS isInCart,
          ci.quantity AS cartQuantity,
          EXISTS (
            SELECT 1
            FROM wishlist_items wi
            WHERE wi.productId = p.id
          ) AS isInWishlist
        FROM cart_items ci
        INNER JOIN products p ON ci.productId = p.id
        LEFT JOIN categories c ON p.categoryId = c.id
        LEFT JOIN manufacturers m ON p.manufacturerId = m.id
        WHERE userId = :userId
    """)
    fun getCartItems(userId: Int): Flow<List<ProductWithState>>

    @Query("SELECT * FROM cart_items WHERE userId = :userId AND productId = :productId LIMIT 1")
    suspend fun getCartItem(userId: Int, productId: Int): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItem)

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE userId = :userId AND productId = :productId")
    suspend fun changeQuantity(userId: Int, productId: Int, quantity: Int)

    @Query("DELETE FROM cart_items WHERE userId = :userId AND productId = :productId")
    suspend fun removeFromCart(userId: Int, productId: Int)

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: Int)
}

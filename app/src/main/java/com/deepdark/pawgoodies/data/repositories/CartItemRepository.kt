package com.deepdark.pawgoodies.data.repositories

import androidx.lifecycle.LiveData
import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.CartItem
import javax.inject.Inject

class CartItemRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getCartItemsLive(
        userId: Int
    ): LiveData<List<CartItem>> = db.cartDao().getCartItems(userId)

    suspend fun addToCart(
        cartItem: CartItem
    ) = db.cartDao().addToCart(cartItem)

    suspend fun removeFromCart(
        cartItem: CartItem
    ) = db.cartDao().removeFromCart(cartItem)

    suspend fun clearCart(
        userId: Int
    ) = db.cartDao().clearCart(userId)
}

package com.deepdark.pawgoodies.data.repositories

import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.CartItem
import com.deepdark.pawgoodies.data.entities.complex.CartItemWithProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartItemRepository @Inject constructor(
    private val db: AppDatabase
) {
    fun getCartItemsWithProducts(userId: Int): Flow<List<CartItemWithProduct>> {
        return db.cartDao().getCartItems(userId)
    }

    suspend fun addToCart(userId: Int, productId: Int, quantity: Int = 1) {
        val existingItem = db.cartDao().getCartItem(userId, productId)

        if (existingItem != null) {
            db.cartDao().updateCartItem(
                existingItem.copy(quantity = existingItem.quantity + quantity)
            )
        } else {
            db.cartDao().addToCart(
                CartItem(
                    userId = userId,
                    productId = productId,
                    quantity = quantity
                )
            )
        }
    }

    suspend fun updateCartItem(cartItem: CartItem) {
        db.cartDao().updateCartItem(cartItem)
    }

    suspend fun removeFromCart(userId: Int, productId: Int) {
        db.cartDao().removeFromCart(userId, productId)
    }

    suspend fun clearCart(userId: Int) {
        db.cartDao().clearCart(userId)
    }
}

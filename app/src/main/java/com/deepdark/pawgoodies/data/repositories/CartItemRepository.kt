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
        return db.cartItemDao().getCartItems(userId)
    }

    suspend fun addToCart(userId: Int, productId: Int, quantity: Int = 1) {
        val existingItem = db.cartItemDao().getCartItem(userId, productId)

        if (existingItem != null) {
            db.cartItemDao().updateCartItem(
                existingItem.copy(quantity = existingItem.quantity + quantity)
            )
        } else {
            db.cartItemDao().addToCart(
                CartItem(
                    userId = userId,
                    productId = productId,
                    quantity = quantity
                )
            )
        }
    }

    suspend fun changeQuantity(userId: Int, productId: Int, quantity: Int) {
        if (quantity > 0) {
            db.cartItemDao().changeQuantity(userId, productId, quantity)
        } else {
            db.cartItemDao().removeFromCart(userId, productId)
        }
    }

    suspend fun removeFromCart(userId: Int, productId: Int) {
        db.cartItemDao().removeFromCart(userId, productId)
    }

    suspend fun clearCart(userId: Int) {
        db.cartItemDao().clearCart(userId)
    }
}

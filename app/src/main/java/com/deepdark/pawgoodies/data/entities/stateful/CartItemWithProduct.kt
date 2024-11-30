package com.deepdark.pawgoodies.data.entities.stateful

import androidx.room.Embedded
import androidx.room.Relation
import com.deepdark.pawgoodies.data.entities.CartItem
import com.deepdark.pawgoodies.data.entities.Product

data class CartItemWithProduct(
    @Embedded val cartItem: CartItem,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product
)
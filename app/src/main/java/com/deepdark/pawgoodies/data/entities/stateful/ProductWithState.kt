package com.deepdark.pawgoodies.data.entities.stateful

data class ProductWithState(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String?,
    val image: String?,

    val categoryId: Int,
    val categoryName: String,

    val manufacturerId: Int,
    val manufacturerName: String,

    val cartQuantity: Int,
    val isInCart: Boolean = false,

    val wishlistId: Int?,
    val isInWishlist: Boolean = false
)
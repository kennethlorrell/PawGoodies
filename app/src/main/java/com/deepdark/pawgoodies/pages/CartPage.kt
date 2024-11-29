package com.deepdark.pawgoodies.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.CartItemCard
import com.deepdark.pawgoodies.components.CartTotal
import com.deepdark.pawgoodies.data.entities.complex.CartItemWithProduct

@Composable
fun CartPage(
    cartItems: List<CartItemWithProduct>,
    totalPrice: Double,
    onAddToCart: (Int) -> Unit,
    onRemoveFromCart: (Int) -> Unit,
    onCheckout: () -> Unit
) {
    if (cartItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Ваш кошик порожній", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onAddToCart = { onAddToCart(cartItem.product.id) },
                        onRemoveFromCart = { onRemoveFromCart(cartItem.product.id) }
                    )
                }
            }

            CartTotal(
                totalPrice = totalPrice,
                onCheckout = onCheckout
            )
        }
    }
}

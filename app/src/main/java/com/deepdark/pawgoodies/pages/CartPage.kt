package com.deepdark.pawgoodies.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.CartItemCard
import com.deepdark.pawgoodies.components.CartTotals
import com.deepdark.pawgoodies.data.entities.complex.CartItemWithProduct

@Composable
fun CartPage(
    cartItems: List<CartItemWithProduct>,
    totalPrice: Double,
    onChangeQuantity: (Int, Int) -> Unit,
    onRemoveFromCart: (Int) -> Unit,
    onCleanCart: () -> Unit
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
            OutlinedButton(
                onClick = onCleanCart,
                contentPadding = PaddingValues(12.dp),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Очистити кошик")
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onChangeQuantity = { item, quantity -> onChangeQuantity(item, quantity) },
                        onRemove = { productId -> onRemoveFromCart(productId) }
                    )
                }
            }

            CartTotals(
                totalPrice = totalPrice,
                onCheckout = onCleanCart
            )
        }
    }
}

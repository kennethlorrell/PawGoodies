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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.CartItemCard
import com.deepdark.pawgoodies.components.CartTotals
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import kotlinx.coroutines.launch

@Composable
fun CartPage(
    cartItems: List<ProductWithState>,
    totalPrice: Double,
    onChangeQuantity: (Int, Int) -> Unit,
    onRemoveFromCart: (Int) -> Unit,
    onCleanCart: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { outerPadding ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(outerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Ваш кошик порожній", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(outerPadding),
            ) {
                Text(
                    text = "Ваш кошик",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(24.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onCleanCart,
                        contentPadding = PaddingValues(12.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary
                        ),
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
                                onChangeQuantity = { item, quantity ->
                                    onChangeQuantity(
                                        item,
                                        quantity
                                    )
                                },
                                onRemove = { productId -> onRemoveFromCart(productId) }
                            )
                            HorizontalDivider()
                        }
                    }

                    CartTotals(
                        totalPrice = totalPrice,
                        onCheckout = {
                            coroutineScope.launch {
                                onCleanCart()
                                snackbarHostState.showSnackbar(
                                    message = "Дякуємо, ваше замовлення успішно оформлено!",
                                    actionLabel = "На головну",
                                    duration = SnackbarDuration.Short
                                )
                                onNavigateToHome()
                            }
                        }
                    )
                }
            }
        }
    }
}

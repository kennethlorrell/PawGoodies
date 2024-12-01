package com.deepdark.pawgoodies.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.WishlistContent
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState

@Composable
fun WishlistPage(
    wishlistItems: List<ProductWithState>,
    onAddToCart: (Int) -> Unit,
    onNavigateToCart: () -> Unit,
    onToggleWishlist: (Int) -> Unit
) {
    if (wishlistItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Ваш список бажань порожній", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(wishlistItems) { product ->
                WishlistContent(
                    product = product,
                    onAddToCart = { onAddToCart(product.id) },
                    onNavigateToCart = onNavigateToCart,
                    onToggleWishlist = { onToggleWishlist(product.id) }
                )
                HorizontalDivider()
            }
        }
    }
}

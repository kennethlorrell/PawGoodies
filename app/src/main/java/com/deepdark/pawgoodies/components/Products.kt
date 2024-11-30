package com.deepdark.pawgoodies.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState

@Composable
fun Products(
    products: List<ProductWithState>,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Int) -> Unit,
    onToggleWishlist: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onProductClick = { onProductClick(product.id) },
                onAddToCart = { onAddToCart(product.id) },
                onToggleWishlist = { onToggleWishlist(product.id) }
            )
        }
    }
}

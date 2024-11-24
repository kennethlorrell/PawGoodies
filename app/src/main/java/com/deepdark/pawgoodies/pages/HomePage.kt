package com.deepdark.pawgoodies.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.CategoryTabs
import com.deepdark.pawgoodies.components.LoadingPlaceholder
import com.deepdark.pawgoodies.components.ProductCard
import com.deepdark.pawgoodies.data.entities.Category
import com.deepdark.pawgoodies.data.entities.Product

@Composable
fun HomePage(
    categories: List<Category>,
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (categories.isEmpty()) {
            LoadingPlaceholder()

            return
        }

        val (selectedCategoryId, setSelectedCategoryId) = remember { mutableStateOf(categories.firstOrNull()?.id) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(
                    WindowInsets
                        .ime
                        .union(WindowInsets.navigationBars)
                        .asPaddingValues()
                )
        ) {
            CategoryTabs(
                categories = categories,
                selectedCategoryId = selectedCategoryId,
                onCategorySelected = setSelectedCategoryId
            )

            val filteredProducts = products.filter {
                it.categoryId == selectedCategoryId
            }

            if (filteredProducts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Для цієї категорії продукти закінчилися ;(.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(product = product, onClick = { onProductClick(product) })
                    }
                }
            }
        }
    }
}

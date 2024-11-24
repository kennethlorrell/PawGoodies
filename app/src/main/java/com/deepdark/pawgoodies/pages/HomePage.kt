package com.deepdark.pawgoodies.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.CategoryTabs
import com.deepdark.pawgoodies.components.LoadingPlaceholder
import com.deepdark.pawgoodies.components.ProductCard
import com.deepdark.pawgoodies.data.Category
import com.deepdark.pawgoodies.data.Product

@Composable
fun HomePage(
    categories: List<Category>,
    products: List<Product>,
    onProductClick: (Product) -> Unit
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
    }

    val filteredProducts = if (selectedCategoryId != null && products.isNotEmpty()) {
//        products.filter {
//            it.categoryId?.id == selectedCategoryId
//        }
        products
    } else {
        products
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(filteredProducts) { product ->
            ProductCard(product = product, onClick = { onProductClick(product) })
        }
    }
}
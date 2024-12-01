package com.deepdark.pawgoodies.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.R
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsPage(
    product: ProductWithState,
    onBack: () -> Unit,
    onAddToCart: (Int) -> Unit,
    onNavigateToCart: () -> Unit,
    onToggleWishlist: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "До списку товарів", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.goodie),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${product.price} ₴",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = product.description ?: "Опис товару відсутній.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Виробник: ${product.manufacturerName}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { if (product.isInCart) onNavigateToCart() else onAddToCart(product.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (product.isInCart) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface,
                        contentColor = if (product.isInCart) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.primary
                    ),
                    border = if (!product.isInCart) BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
                    ) else null
                ) {
                    Text(text = if (product.isInCart) "У кошику" else "До кошика")
                }

                Button(
                    onClick = { onToggleWishlist(product.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (product.isInWishlist) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface,
                        contentColor = if (product.isInWishlist) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.primary
                    ),
                    border = if (!product.isInWishlist) BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
                    ) else null
                ) {
                    Text(text = if (product.isInWishlist) "У списку бажань" else "До списку бажань")
                }
            }
        }
    }
}

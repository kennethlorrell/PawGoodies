package com.deepdark.pawgoodies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.R
import com.deepdark.pawgoodies.data.entities.complex.CartItemWithProduct

@Composable
fun CartItemCard(
    cartItem: CartItemWithProduct,
    onChangeQuantity: (Int, Int) -> Unit,
    onRemove: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.goodie),
            contentDescription = cartItem.product.name,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                cartItem.product.name,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.padding(2.dp))

            Text(
                "${cartItem.product.price} ₴",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.surfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onChangeQuantity(cartItem.product.id, cartItem.cartItem.quantity - 1) }) {
                Icon(Icons.Default.Remove, contentDescription = "Зменшити кількість")
            }

            Text("${cartItem.cartItem.quantity}", style = MaterialTheme.typography.bodyMedium)

            IconButton(onClick = { onChangeQuantity(cartItem.product.id, cartItem.cartItem.quantity + 1) }) {
                Icon(Icons.Default.Add, contentDescription = "Збільшити кількість")
            }
        }

        IconButton(onClick = { onRemove(cartItem.product.id) }) {
            Icon(Icons.Default.Delete, contentDescription = "Видалити")
        }
    }
}

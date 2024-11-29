package com.deepdark.pawgoodies.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(
    message: String?,
    modifier: Modifier = Modifier
) {
    if (!message.isNullOrEmpty()) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = modifier.padding(top = 16.dp)
        )
    }
}
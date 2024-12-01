package com.deepdark.pawgoodies.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordField(
    password: String,
    label: String,
    isPasswordVisible: Boolean,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None
                               else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility
                                  else Icons.Filled.VisibilityOff,
                    contentDescription = "Переглянути пароль"
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

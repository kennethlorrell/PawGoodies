package com.deepdark.pawgoodies.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.ErrorMessage
import com.deepdark.pawgoodies.data.entities.User
import com.deepdark.pawgoodies.data.viewmodels.ErrorState

@Composable
fun UserProfilePage(
    user: User?,
    errorState: ErrorState,
    onUpdateUserDetails: (String, String) -> Unit,
    onUpdatePassword: (String, String) -> Unit,
    onLogout: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        if (user != null) {
            name = user!!.name
            email = user!!.email
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Привіт, ${user?.name ?: "користувач"}!",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ім'я") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Електронна пошта") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Зміна пароля:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            label = { Text("Поточний пароль") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Переглянути пароль"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Новий пароль") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Переглянути пароль"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        ErrorMessage(message = errorState.message)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onUpdateUserDetails(name, email) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Оновити дані")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onUpdatePassword(currentPassword, newPassword) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Змінити пароль")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Вийти з акаунту")
        }
    }
}

package com.deepdark.pawgoodies.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.PasswordField
import com.deepdark.pawgoodies.components.TextMessage
import com.deepdark.pawgoodies.data.entities.User
import com.deepdark.pawgoodies.data.viewmodels.MessageState

@Composable
fun UserProfilePage(
    user: User?,
    messageState: MessageState?,
    onUpdateUserDetails: (String, String) -> Unit,
    onUpdatePassword: (String, String) -> Unit,
    onLogout: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        if (user != null) {
            name = user.name
            email = user.email
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

        Button(
            onClick = { onUpdateUserDetails(name, email) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Оновити дані")
        }

        HorizontalDivider()

        PasswordField(
            password = currentPassword,
            isPasswordVisible = currentPasswordVisible,
            label = "Поточний пароль",
            onValueChange = { value -> currentPassword = value },
            onClick = { currentPasswordVisible = !currentPasswordVisible }
        )

        PasswordField(
            password = newPassword,
            label = "Новий пароль",
            isPasswordVisible = newPasswordVisible,
            onValueChange = { value -> newPassword = value },
            onClick = { newPasswordVisible = !newPasswordVisible }
        )

        Button(
            onClick = { onUpdatePassword(currentPassword, newPassword) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Змінити пароль")
        }

        HorizontalDivider()

        TextMessage(messageState = messageState)

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


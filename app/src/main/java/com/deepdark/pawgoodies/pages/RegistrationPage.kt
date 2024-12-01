package com.deepdark.pawgoodies.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.TextMessage
import com.deepdark.pawgoodies.components.StyledTextField
import com.deepdark.pawgoodies.data.viewmodels.AuthState
import com.deepdark.pawgoodies.data.viewmodels.MessageState

@Composable
fun RegistrationPage(
    authState: AuthState,
    messageState: MessageState?,
    onRegisterClick: (String, String, String) -> Unit,
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StyledTextField(
            value = name,
            onValueChange = { name = it },
            label = "Як вас звати?"
        )

        StyledTextField(
            value = email,
            onValueChange = { email = it },
            label = "Електронна пошта"
        )

        Spacer(modifier = Modifier.height(8.dp))

        StyledTextField(
            value = password,
            onValueChange = { password = it },
            label = "Пароль",
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextMessage(messageState = messageState)

        Button(onClick = { onRegisterClick(name, email, password) }) {
            Text("Зареєструватися")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onLoginClick) {
            Text("Вже зареєстровані? Увійти")
        }
    }
}

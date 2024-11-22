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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.deepdark.pawgoodies.components.StyledTextField
import com.deepdark.pawgoodies.data.getCustomErrorMessage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginPage(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StyledTextField(
            value = email,
            onValueChange = { email = it },
            label = "Електронна пошта"
        )

        Spacer(modifier = Modifier.height(8.dp))

        StyledTextField(
            value = password,
            onValueChange = { password = it },
            label = "Пароль"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onLoginSuccess()
                    }
                }
                .addOnFailureListener { exception ->
                    errorMessage = getCustomErrorMessage(exception)
                }
        }) {
            Text("Увійти")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onRegisterClick) {
            Text("Не маєте облікового запису? Зареєструватися")
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Помилка авторизації",
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
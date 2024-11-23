package com.deepdark.pawgoodies.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UserProfilePage(
    onLogout: () -> Unit
) {
    Column() {
        Button(onClick = onLogout) {
            Text("Вийти з акаунту")
        }
    }
}
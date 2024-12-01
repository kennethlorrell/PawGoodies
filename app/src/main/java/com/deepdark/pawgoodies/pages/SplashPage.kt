package com.deepdark.pawgoodies.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.deepdark.pawgoodies.data.NavigationPage
import com.deepdark.pawgoodies.data.viewmodels.AuthState

@Composable
fun SplashPage(
    authState: AuthState,
    onNavigate: (String) -> Unit
) {
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> onNavigate(NavigationPage.Home.route)
            else -> onNavigate(NavigationPage.Login.route)
        }
    }
}

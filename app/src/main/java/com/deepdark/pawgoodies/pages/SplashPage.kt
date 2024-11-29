package com.deepdark.pawgoodies.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.deepdark.pawgoodies.data.NavigationPage
import com.deepdark.pawgoodies.data.viewmodels.AuthState
import com.deepdark.pawgoodies.data.viewmodels.AuthViewModel

@Composable
fun SplashPage(onNavigate: (String) -> Unit) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> onNavigate(NavigationPage.Home.route)
            else -> onNavigate(NavigationPage.Login.route)
        }
    }
}

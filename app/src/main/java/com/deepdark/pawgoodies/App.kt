package com.deepdark.pawgoodies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepdark.pawgoodies.pages.HomePage
import com.deepdark.pawgoodies.pages.LoginPage
import com.deepdark.pawgoodies.pages.Page
import com.deepdark.pawgoodies.pages.RegistrationPage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun App() {
    val auth = FirebaseAuth.getInstance()

    val currentUser = remember { auth.currentUser }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) Page.Home.route else Page.Login.route) {
        composable(Page.Login.route) {
            LoginPage(
                onLoginSuccess = { navController.navigate(Page.Home.route) },
                onRegisterClick = { navController.navigate(Page.Registration.route) }
            )
        }

        composable(Page.Registration.route) {
            RegistrationPage(
                onRegisterSuccess = { navController.navigate(Page.Home.route) },
                onLoginClick = { navController.navigate(Page.Login.route) }
            )
        }

        composable(Page.Home.route) {
            HomePage(
                onLogout = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Page.Login.route) {
                        popUpTo(Page.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
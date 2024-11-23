package com.deepdark.pawgoodies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepdark.pawgoodies.components.BottomNavBar
import com.deepdark.pawgoodies.components.TopBar
import com.deepdark.pawgoodies.pages.CartPage
import com.deepdark.pawgoodies.pages.HomePage
import com.deepdark.pawgoodies.pages.LoginPage
import com.deepdark.pawgoodies.data.NavigationPage
import com.deepdark.pawgoodies.pages.PetProfilePage
import com.deepdark.pawgoodies.pages.RegistrationPage
import com.deepdark.pawgoodies.pages.UserProfilePage
import com.deepdark.pawgoodies.pages.WishlistPage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun App() {
    val auth = FirebaseAuth.getInstance()
    val currentUser = remember { auth.currentUser }

    val navController = rememberNavController()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = if (currentUser != null) NavigationPage.Home.route else NavigationPage.Login.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(NavigationPage.Login.route) {
                    LoginPage(
                        onLoginSuccess = { navController.navigate(NavigationPage.Home.route) },
                        onRegisterClick = { navController.navigate(NavigationPage.Registration.route) }
                    )
                }

                composable(NavigationPage.Registration.route) {
                    RegistrationPage(
                        onRegisterSuccess = { navController.navigate(NavigationPage.Home.route) },
                        onLoginClick = { navController.navigate(NavigationPage.Login.route) }
                    )
                }

                composable(NavigationPage.Home.route) {
                    HomePage(
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate(NavigationPage.Login.route) {
                                popUpTo(NavigationPage.Home.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(NavigationPage.Cart.route) { CartPage() }
                composable(NavigationPage.Wishlist.route) { WishlistPage() }
                composable(NavigationPage.PetProfile.route) { PetProfilePage() }
                composable(NavigationPage.UserProfile.route) { UserProfilePage() }
            }
        }
    }
}


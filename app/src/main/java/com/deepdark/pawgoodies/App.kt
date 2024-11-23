package com.deepdark.pawgoodies

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.deepdark.pawgoodies.components.BottomNavBar
import com.deepdark.pawgoodies.components.TopBar
import com.deepdark.pawgoodies.pages.CartPage
import com.deepdark.pawgoodies.pages.HomePage
import com.deepdark.pawgoodies.pages.LoginPage
import com.deepdark.pawgoodies.pages.Page
import com.deepdark.pawgoodies.pages.PetProfilePage
import com.deepdark.pawgoodies.pages.RegistrationPage
import com.deepdark.pawgoodies.pages.UserProfilePage
import com.deepdark.pawgoodies.pages.WishlistPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun App(
    navController: NavHostController,
    scrollState: ScrollState,
    currentUser: FirebaseUser?
) {
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (currentUser != null) Page.Home.route else Page.Login.route,
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(innerPadding)
        ) {
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

            composable(Page.Cart.route) { CartPage() }
            composable(Page.Wishlist.route) { WishlistPage() }
            composable(Page.PetProfile.route) { PetProfilePage() }
            composable(Page.UserProfile.route) { UserProfilePage() }
        }
    }
}


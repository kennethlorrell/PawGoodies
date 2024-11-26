package com.deepdark.pawgoodies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.deepdark.pawgoodies.components.LoadingPlaceholder
import com.deepdark.pawgoodies.components.ProductDetailsPage
import com.deepdark.pawgoodies.components.ProductNotFound
import com.deepdark.pawgoodies.pages.CartPage
import com.deepdark.pawgoodies.pages.HomePage
import com.deepdark.pawgoodies.pages.LoginPage
import com.deepdark.pawgoodies.data.NavigationPage
import com.deepdark.pawgoodies.pages.PetProfilePage
import com.deepdark.pawgoodies.pages.RegistrationPage
import com.deepdark.pawgoodies.pages.UserProfilePage
import com.deepdark.pawgoodies.pages.WishlistPage
import androidx.navigation.compose.rememberNavController
import com.deepdark.pawgoodies.components.BottomNavBar
import com.deepdark.pawgoodies.components.TopBar
import com.deepdark.pawgoodies.data.viewmodels.SharedViewModel

@Composable
fun App() {
    val sharedViewModel: SharedViewModel = hiltViewModel()

    val categories by sharedViewModel.categories.observeAsState(emptyList())
    val products by sharedViewModel.products.observeAsState(emptyList())

    val navController = rememberNavController()

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
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationPage.Home.route
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
                        categories = categories,
                        products = products,
                        onProductClick = { product ->
                            navController.navigate("${NavigationPage.ProductDetail.route}/${product.id}")
                        }
                    )
                }

                composable(NavigationPage.Cart.route) { CartPage() }
                composable(NavigationPage.Wishlist.route) { WishlistPage() }
                composable(NavigationPage.PetProfile.route) { PetProfilePage() }

                composable(NavigationPage.UserProfile.route) {
                    UserProfilePage(
                        onLogout = {
                            // TODO: Implement logout
                            navController.navigate(NavigationPage.Login.route) {
                                popUpTo(NavigationPage.Home.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    route = "productDetail/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getInt("productId")

                    if (productId == null) {
                        ProductNotFound(
                            errorMessage = "Помилка: продукт не знайдено",
                            onBack = { navController.popBackStack() }
                        )
                    } else {
                        val product by sharedViewModel.getProductById(productId).observeAsState()

                        when {
                            product == null -> {
                                LoadingPlaceholder()
                            }
                            else -> {
                                ProductDetailsPage(
                                    product = product!!,
                                    onBack = { navController.popBackStack() },
                                    onAddToCart = { /* TODO: Implement cart */ },
                                    onAddToWishlist = { /* TODO: Implement wishlist */ }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


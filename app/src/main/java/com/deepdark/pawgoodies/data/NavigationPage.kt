package com.deepdark.pawgoodies.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Satellite
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationPage(val route: String, val title: String, val icon: ImageVector) {
    object Login : NavigationPage("login", "Вхід", Icons.Outlined.Lock)
    object Registration : NavigationPage("register", "Реєстрація", Icons.Outlined.PersonAdd)

    object Home : NavigationPage("home", "Головна", Icons.Outlined.Home)
    object Cart : NavigationPage("cart", "Кошик", Icons.Outlined.ShoppingCart)
    object Wishlist : NavigationPage("wishlist", "Список бажань", Icons.Outlined.FavoriteBorder)
    object PetProfile : NavigationPage("pet_profile", "Профіль тварини", Icons.Outlined.Pets)
    object UserProfile : NavigationPage("user_profile", "Профіль користувача", Icons.Outlined.AccountCircle)
    object ProductDetail : NavigationPage("productDetail", "Детальна сторінка продукту", Icons.Outlined.Satellite)
}
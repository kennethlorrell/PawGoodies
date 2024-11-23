package com.deepdark.pawgoodies.pages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AppRegistration
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Page(val route: String, val title: String, val icon: ImageVector) {
    object Login : Page("login", "Вхід", Icons.AutoMirrored.Outlined.Login)
    object Registration : Page("registration", "Реєстрація", Icons.Outlined.AppRegistration)

    object Home : Page("home", "Головна", Icons.Outlined.Home)
    object Cart : Page("cart", "Кошик", Icons.Outlined.ShoppingCart)
    object Wishlist : Page("wishlist", "Список бажань", Icons.Outlined.FavoriteBorder)
    object PetProfile : Page("pet_profile", "Профіль тварини", Icons.TwoTone.Pets)
    object UserProfile : Page("user_profile", "Профіль", Icons.Outlined.AccountCircle)
}
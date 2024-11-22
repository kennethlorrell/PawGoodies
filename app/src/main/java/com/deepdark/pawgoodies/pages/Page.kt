package com.deepdark.pawgoodies.pages

sealed class Page(val route: String) {
    object Login : Page("login")
    object Registration : Page("registration")
    object Home : Page("home")
}
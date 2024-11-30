package com.deepdark.pawgoodies.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.union
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.deepdark.pawgoodies.data.NavigationPage

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        NavigationPage.Home,
        NavigationPage.Cart,
        NavigationPage.Wishlist,
        NavigationPage.UserProfile
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .consumeWindowInsets(
                WindowInsets
                    .ime
                    .union(WindowInsets.navigationBars)
                    .asPaddingValues()
            )
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        items.forEach { page ->
            val isSelected = currentRoute == page.route

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = page.icon,
                        contentDescription = page.title,
                        tint = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = {
                    Text(
                        text = page.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                },
                selected = isSelected,
                onClick = {
                    if (currentRoute != page.route) {
                        navController.navigate(page.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.inversePrimary
                ),
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}

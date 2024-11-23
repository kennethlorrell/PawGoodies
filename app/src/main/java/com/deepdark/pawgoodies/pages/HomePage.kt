package com.deepdark.pawgoodies.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.union
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.deepdark.pawgoodies.components.CategoryTabs
import com.deepdark.pawgoodies.viewmodel.CategoryViewModel

@Composable
fun HomePage(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    onCategorySelected: (String?) -> Unit
) {
    val categories by categoryViewModel.categories.collectAsState()
    val isLoading by categoryViewModel.isLoading.collectAsState()
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val (selectedCategoryId, setSelectedCategoryId) = remember { mutableStateOf(categories.firstOrNull()?.id) }

    if (isLoading) {
        CircularProgressIndicator()
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(
                    WindowInsets
                        .ime
                        .union(WindowInsets.navigationBars)
                        .asPaddingValues()
                )
        ) {
            CategoryTabs(
                categories = categories,
                selectedCategoryId = selectedCategoryId,
                onCategorySelected = setSelectedCategoryId
            )
        }
    }
}
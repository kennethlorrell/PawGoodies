package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.SessionManager
import com.deepdark.pawgoodies.data.entities.Category
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import com.deepdark.pawgoodies.data.repositories.CategoryRepository
import com.deepdark.pawgoodies.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    val categories: Flow<List<Category>> = categoryRepository.getAllCategories()

    private val _products = MutableStateFlow<List<ProductWithState>>(emptyList())
    val products: StateFlow<List<ProductWithState>> = _products

    init {
        viewModelScope.launch {
            sessionManager.userId.collectLatest { userId ->
                if (userId != null) {
                    productRepository.getAllProductsWithState(userId).collect { items ->
                        _products.value = items
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getProductById(productId: Int): Flow<ProductWithState?> {
        return sessionManager.userId.flatMapLatest { userId ->
            if (userId != null) {
                productRepository.getProductWithStateById(userId, productId)
            } else {
                flowOf(null)
            }
        }
    }
}

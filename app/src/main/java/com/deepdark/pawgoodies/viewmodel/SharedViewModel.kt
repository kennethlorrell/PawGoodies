package com.deepdark.pawgoodies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.Category
import com.deepdark.pawgoodies.data.Product
import com.deepdark.pawgoodies.repository.CategoryRepository
import com.deepdark.pawgoodies.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        fetchCategories()
        fetchProducts()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                _categories.value = categoryRepository.fetchCategories()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                _products.value = productRepository.fetchProducts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

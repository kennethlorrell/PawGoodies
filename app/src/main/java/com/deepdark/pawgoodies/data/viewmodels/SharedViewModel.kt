package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.deepdark.pawgoodies.data.entities.Category
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import com.deepdark.pawgoodies.data.repositories.CategoryRepository
import com.deepdark.pawgoodies.data.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    val categories: LiveData<List<Category>> = categoryRepository.getAllCategoriesLive()
    val products: LiveData<List<ProductWithState>> = productRepository.getAllProductsLive()

    fun getProductById(productId: Int): LiveData<ProductWithState?> {
        return productRepository.getProductByIdLive(productId)
    }
}

package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.SessionManager
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import com.deepdark.pawgoodies.data.repositories.CartItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartItemRepository: CartItemRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<ProductWithState>>(emptyList())
    val cartItems: StateFlow<List<ProductWithState>> = _cartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    init {
        viewModelScope.launch {
            sessionManager.userId.collectLatest { userId ->
                if (userId != null) {
                    cartItemRepository.getCartItems(userId).collect { items ->
                        _cartItems.value = items
                        calculateTotal(items)
                    }
                }
            }
        }
    }

    private fun calculateTotal(items: List<ProductWithState>) {
        _totalPrice.value = items.sumOf { it.cartQuantity * it.price }
    }

    fun addToCart(productId: Int, quantity: Int = 1) {
        viewModelScope.launch {
            sessionManager.userId.collectLatest { userId ->
                if (userId != null) {
                    cartItemRepository.addToCart(userId, productId, quantity)
                }
            }
        }
    }

    fun changeQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            sessionManager.userId.collectLatest { userId ->
                if (userId != null) {
                    cartItemRepository.changeQuantity(userId, productId, quantity)
                }
            }
        }
    }

    fun removeItem(productId: Int) {
        viewModelScope.launch {
            sessionManager.userId.collectLatest { userId ->
                if (userId != null) {
                    cartItemRepository.removeFromCart(userId, productId)
                }
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            sessionManager.userId.collectLatest { userId ->
                if (userId != null) {
                    cartItemRepository.clearCart(userId)
                }
            }
        }
    }
}

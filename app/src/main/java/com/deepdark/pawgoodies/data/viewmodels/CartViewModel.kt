package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.SessionManager
import com.deepdark.pawgoodies.data.entities.CartItem
import com.deepdark.pawgoodies.data.repositories.CartItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartItemRepository: CartItemRepository,
    sessionManager: SessionManager
) : ViewModel() {

    private val userIdLiveData = sessionManager.userIdLiveData

    val cartItems: MediatorLiveData<List<CartItem>> = MediatorLiveData<List<CartItem>>().apply {
        addSource(userIdLiveData) { userId ->
            if (userId != null) {
                addSource(cartItemRepository.getCartItemsLive(userId)) { cartList ->
                    value = cartList
                }
            } else {
                value = emptyList()
            }
        }
    }

    fun addToCart(productId: Int, quantity: Int) {
        userIdLiveData.value?.let { userId ->
            viewModelScope.launch {
                cartItemRepository.addToCart(
                    CartItem(
                        userId = userId,
                        productId = productId,
                        quantity = quantity
                    )
                )
            }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartItemRepository.removeFromCart(cartItem)
        }
    }
}

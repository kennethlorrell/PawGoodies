package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.SessionManager
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState
import com.deepdark.pawgoodies.data.repositories.WishlistItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistItemRepository: WishlistItemRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _wishlistItems = MutableStateFlow<List<ProductWithState>>(emptyList())
    val wishlistItems: StateFlow<List<ProductWithState>> = _wishlistItems

    init {
        viewModelScope.launch {
            sessionManager.userId.collectLatest { userId ->
                if (userId != null) {
                    wishlistItemRepository.getWishlistItems(userId).collect { items ->
                        _wishlistItems.value = items
                    }
                }
            }
        }
    }

    fun toggleWishlistItem(productId: Int) {
        viewModelScope.launch {
            sessionManager.userId.collectLatest { userId ->
                if (userId != null) {
                    wishlistItemRepository.toggleWishlistItem(userId, productId)
                }
            }
        }
    }
}

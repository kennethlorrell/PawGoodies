package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.SessionManager
import com.deepdark.pawgoodies.data.entities.WishlistItem
import com.deepdark.pawgoodies.data.repositories.WishlistItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistItemRepository: WishlistItemRepository,
    sessionManager: SessionManager
) : ViewModel() {

    private val userIdLiveData = sessionManager.userIdLiveData

    val wishlistItems: MediatorLiveData<List<WishlistItem>> = MediatorLiveData<List<WishlistItem>>().apply {
        addSource(userIdLiveData) { userId ->
            if (userId != null) {
                addSource(wishlistItemRepository.getWishlistItemsLive(userId)) { wishlist ->
                    value = wishlist
                }
            } else {
                value = emptyList()
            }
        }
    }

    fun addToWishlist(
        productId: Int
    ) {
        userIdLiveData.value?.let { userId ->
            viewModelScope.launch {
                wishlistItemRepository.addToWishlist(
                    WishlistItem(
                        userId = userId,
                        productId = productId
                    )
                )
            }
        }
    }

    fun removeFromWishlist(
        wishlistItem: WishlistItem
    ) {
        viewModelScope.launch {
            wishlistItemRepository.removeFromWishlist(wishlistItem)
        }
    }
}

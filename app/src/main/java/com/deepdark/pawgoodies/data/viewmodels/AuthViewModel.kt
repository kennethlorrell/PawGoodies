package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.User
import kotlinx.coroutines.launch

class AuthViewModel(private val db: AppDatabase) : ViewModel() {
    var currentUser: MutableLiveData<User?> = MutableLiveData(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = db.userDao().authenticate(email, password)
            currentUser.postValue(user)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val userId = db.userDao().insertUser(
                    User(
                        email = email,
                        password = password
                    )
            )

            if (userId > 0) {
                val user = db.userDao().getUserById(userId.toInt())
                currentUser.postValue(user)
            }
        }
    }

    fun logout() {
        currentUser.postValue(null)
    }
}


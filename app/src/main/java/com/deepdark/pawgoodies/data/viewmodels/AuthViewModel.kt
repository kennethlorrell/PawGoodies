package com.deepdark.pawgoodies.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.SessionManager
import com.deepdark.pawgoodies.data.entities.User
import com.deepdark.pawgoodies.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    init {
        restoreSession()
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (email.isBlank()) {
                _authState.value = AuthState.Error("Електронна пошта не може бути пустою")
                return@launch
            }

            if (password.isBlank()) {
                _authState.value = AuthState.Error("Пароль не може бути пустим")
                return@launch
            }

            val user = userRepository.authenticateUser(email, password)

            if (user != null && user.password == password) {
                sessionManager.saveUserSession(user.id)
                _authState.value = AuthState.Authenticated(user)
            } else {
                _authState.value = AuthState.Error("Не знайдено користувача з такими обліковими даними \uD83D\uDE3F")
            }
        }
    }

    fun register(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (email.isBlank()) {
                _authState.value = AuthState.Error("Електронна пошта не може бути пустою")
                return@launch
            }

            if (password.isBlank()) {
                _authState.value = AuthState.Error("Пароль не може бути пустим")
                return@launch
            }

            val existingUser = userRepository.getUserByEmail(email)

            if (existingUser != null) {
                _authState.value = AuthState.Error("Цей email вже зайнято \uD83D\uDE3F")
                return@launch
            }

            val newUser = User(
                email = email,
                password = password
            )

            userRepository.insertUser(newUser)
            _authState.value = AuthState.Registered
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _authState.value = AuthState.Idle
        }
    }

    fun restoreSession() {
        viewModelScope.launch {
            sessionManager.userId.collect { userId ->
                if (userId != null) {
                    val user = userRepository.getUserById(userId)
                    if (user != null) {
                        _authState.value = AuthState.Authenticated(user)
                    } else {
                        _authState.value = AuthState.Idle
                    }
                }
            }
        }
    }
}

sealed class AuthState {
    data object Idle : AuthState()
    data object Registered : AuthState()
    data class Authenticated(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

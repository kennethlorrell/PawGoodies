package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.SessionManager
import com.deepdark.pawgoodies.data.entities.User
import com.deepdark.pawgoodies.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _errorState = MutableStateFlow(ErrorState())
    val errorState: StateFlow<ErrorState> = _errorState

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        restoreSession()
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (email.isBlank()) {
                _errorState.value = ErrorState("Електронна пошта не може бути пустою")
                return@launch
            }

            if (password.isBlank()) {
                _errorState.value = ErrorState("Пароль не може бути пустим")
                return@launch
            }

            val user = userRepository.authenticateUser(email, password)

            if (user != null && user.password == password) {
                sessionManager.saveUserSession(user.id)
                _authState.value = AuthState.Authenticated(user)
                _user.value = user
                _errorState.value = ErrorState(null)
            } else {
                _errorState.value = ErrorState("Не знайдено користувача з такими обліковими даними \uD83D\uDE3F")
            }
        }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (name.isBlank()) {
                _errorState.value = ErrorState("Будь-ласка, вкажіть ваше ім’я")
                return@launch
            }

            if (email.isBlank()) {
                _errorState.value = ErrorState("Електронна пошта не може бути пустою")
                return@launch
            }

            if (password.isBlank()) {
                _errorState.value = ErrorState("Пароль не може бути пустим")
                return@launch
            }

            val existingUser = userRepository.getUserByEmail(email)

            if (existingUser != null) {
                _errorState.value = ErrorState("Цей email вже зайнято \uD83D\uDE3F")
                return@launch
            }

            val newUser = User(
                name = name,
                email = email,
                password = password
            )

            userRepository.insertUser(newUser)
            sessionManager.saveUserSession(newUser.id)
            _authState.value = AuthState.Authenticated(newUser)
            _user.value = newUser
            _errorState.value = ErrorState(null)
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _authState.value = AuthState.Idle
            _user.value = null
        }
    }

    fun updateUserDetails(
        name: String,
        email: String
    ) {
        viewModelScope.launch {
            if (name.isBlank()) {
                _errorState.value = ErrorState("Ім’я не може бути пустим")
                return@launch
            }

            if (email.isBlank()) {
                _errorState.value = ErrorState("Електронна пошта не може бути пустою")
                return@launch
            }

            val existingUser = userRepository.getUserByEmail(email)
            val currentUser = (_authState.value as? AuthState.Authenticated)?.user

            if (existingUser?.id != currentUser?.id) {
                _errorState.value = ErrorState("Такий email вже зайнято \uD83D\uDE3F")
                return@launch
            }

            if (currentUser != null) {
                userRepository.updateUserDetails(currentUser.id, name, email)
            }

            _errorState.value = ErrorState(null)
        }
    }

    fun updatePassword(
        currentPassword: String,
        newPassword: String
    ) {
        viewModelScope.launch {
            if (newPassword.isBlank()) {
                _errorState.value = ErrorState("Новий пароль не може бути пустим")
                return@launch
            }

            val currentUser = (_authState.value as? AuthState.Authenticated)?.user

            if (currentUser != null) {
                if (currentPassword == currentUser.password) {
                    userRepository.updateUserPassword(currentUser.id, newPassword)
                    _errorState.value = ErrorState(null)
                } else {
                    _errorState.value = ErrorState("Неправильний поточний пароль")
                }
            }
        }
    }

    private fun restoreSession() {
        viewModelScope.launch {
            sessionManager.userId.collect { userId ->
                if (userId != null) {
                    val user = userRepository.getUserById(userId)

                    if (user != null) {
                        _authState.value = AuthState.Authenticated(user)
                        _user.value = user
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
    data class Authenticated(val user: User, val errorMessage: String? = null) : AuthState()
}

data class ErrorState(
    val message: String? = null
)

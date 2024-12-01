package com.deepdark.pawgoodies.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepdark.pawgoodies.data.SessionManager
import com.deepdark.pawgoodies.data.entities.User
import com.deepdark.pawgoodies.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val _messageState = MutableStateFlow<MessageState?>(null)
    val messageState: StateFlow<MessageState?> = _messageState

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val MESSAGE_DISPLAY_DURATION = 7000L

    init {
        restoreSession()
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (email.isBlank()) {
                showMessage("Електронна пошта не може бути пустою", MessageType.ERROR)
                return@launch
            }

            if (password.isBlank()) {
                showMessage("Пароль не може бути пустим", MessageType.ERROR)
                return@launch
            }

            val user = userRepository.authenticateUser(email, password)

            if (user != null && user.password == password) {
                sessionManager.saveUserSession(user.id)
                _authState.value = AuthState.Authenticated(user)
                _user.value = user
                showMessage("Вітаємо, ${user.name}", MessageType.SUCCESS)
            } else {
                showMessage("Не знайдено користувача з такими обліковими даними \uD83D\uDE3F", MessageType.ERROR)
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
                showMessage("Будь-ласка, вкажіть ваше ім’я", MessageType.ERROR)
                return@launch
            }

            if (email.isBlank()) {
                showMessage("Електронна пошта не може бути пустою", MessageType.ERROR)
                return@launch
            }

            if (password.isBlank()) {
                showMessage("Пароль не може бути пустим", MessageType.ERROR)
                return@launch
            }

            val existingUser = userRepository.getUserByEmail(email)

            if (existingUser != null) {
                showMessage("Цей email вже зайнято \uD83D\uDE3F", MessageType.ERROR)
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
            showMessage("Ласкаво просимо, ${newUser.name}", MessageType.SUCCESS)
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
                showMessage("Ім’я не може бути пустим", MessageType.ERROR)
                return@launch
            }

            if (email.isBlank()) {
                showMessage("Електронна пошта не може бути пустою", MessageType.ERROR)
                return@launch
            }

            val currentUser = (_authState.value as? AuthState.Authenticated)?.user

            if (currentUser != null) {
                userRepository.updateUserDetails(currentUser.id, name, email)
            }

            showMessage("Дані успішно оновлено", MessageType.SUCCESS)
        }
    }

    fun updatePassword(
        currentPassword: String,
        newPassword: String
    ) {
        viewModelScope.launch {
            if (newPassword.isBlank()) {
                showMessage("Новий пароль не може бути пустим", MessageType.ERROR)
                return@launch
            }

            val currentUser = (_authState.value as? AuthState.Authenticated)?.user

            if (currentUser != null) {
                if (currentPassword == currentUser.password) {
                    userRepository.updateUserPassword(currentUser.id, newPassword)
                    showMessage("Пароль успішно оновлено", MessageType.SUCCESS)
                } else {
                    showMessage("Неправильний поточний пароль", MessageType.ERROR)
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

    private fun showMessage(message: String, type: MessageType) {
        _messageState.value = MessageState(message, type)

        viewModelScope.launch {
            delay(MESSAGE_DISPLAY_DURATION)
            _messageState.value = null
        }
    }
}

sealed class AuthState {
    data object Idle : AuthState()
    data class Authenticated(val user: User, val errorMessage: String? = null) : AuthState()
}

data class MessageState(
    val message: String = "",
    val type: MessageType = MessageType.INFO
)

enum class MessageType {
    SUCCESS,
    ERROR,
    INFO
}

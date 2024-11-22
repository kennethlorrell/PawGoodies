package com.deepdark.pawgoodies.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

fun isAuthenticated(): Boolean {
    return FirebaseAuth.getInstance().currentUser != null
}

fun getCustomErrorMessage(exception: Exception): String {
    val errorCode = (exception as? FirebaseAuthException)?.errorCode
    return when (errorCode) {
        "ERROR_INVALID_EMAIL" -> "Неправильний формат електронної пошти. Перевірте дані та спробуйте ще раз."
        "ERROR_USER_DISABLED" -> "Цей акаунт було заблоковано."
        "ERROR_WRONG_PASSWORD" -> "Неправильний пароль. Спробуйте ще раз."
        "ERROR_USER_NOT_FOUND" -> "Користувача з такою електронною поштою не існує."
        "ERROR_NETWORK_REQUEST_FAILED" -> "Відсутнє з'єднання з мережею. Будь ласка, перевірте інтернет."
        "ERROR_EMAIL_ALREADY_IN_USE" -> "Ця електронна пошта вже використовується."
        "ERROR_WEAK_PASSWORD" -> "Пароль занадто слабкий. Використовуйте не менше 6 символів."
        else -> "Сталася помилка: ${exception.localizedMessage ?: "невідома помилка"}"
    }
}
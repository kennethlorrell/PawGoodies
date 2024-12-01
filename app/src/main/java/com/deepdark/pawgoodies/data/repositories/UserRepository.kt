package com.deepdark.pawgoodies.data.repositories

import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun registerUser(
        user: User
    ): Long {
        return db.userDao().insertUser(user)
    }

    suspend fun authenticateUser(
        email: String,
        password: String
    ): User? {
        return db.userDao().getUserByEmailAndPassword(email, password)
    }

    suspend fun getUserById(
        userId: Int
    ): User? {
        return db.userDao().getUserById(userId)
    }

    suspend fun getUserByEmail(
        email: String
    ): User? {
        return db.userDao().getUserByEmail(email)
    }

    suspend fun insertUser(
        user: User
    ): Long = db.userDao().insertUser(user)

    suspend fun updateUserDetails(
        userId: Int,
        name: String,
        email: String
    ) {
        db.userDao().updateUserDetails(
            userId = userId,
            name = name,
            email = email
        )
    }

    suspend fun updateUserPassword(
        userId: Int,
        password: String
    ) {
        db.userDao().updateUserPassword(
            userId = userId,
            password = password
        )
    }
}

package com.deepdark.pawgoodies.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Insert
    suspend fun insertUsers(users: List<User>)

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @Query("UPDATE users SET name = :name, email = :email WHERE id = :userId")
    suspend fun updateUserDetails(userId: Int, name: String, email: String)

    @Query("UPDATE users SET password = :password WHERE id = :userId")
    suspend fun updateUserPassword(userId: Int, password: String)
}

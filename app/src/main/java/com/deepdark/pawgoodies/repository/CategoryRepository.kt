package com.deepdark.pawgoodies.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.deepdark.pawgoodies.data.Category

class CategoryRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun fetchCategories(): List<Category> {
        val categories = mutableListOf<Category>()

        try {
            val snapshot = firestore.collection("categories")
                .get()
                .await()

            for (document in snapshot.documents) {
                val id = document.id
                val name = document.getString("name") ?: "Unknown"
                val image = document.getString("image")

                categories.add(Category(id, name, image))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return categories
    }
}
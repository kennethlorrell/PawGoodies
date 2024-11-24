package com.deepdark.pawgoodies.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.deepdark.pawgoodies.data.Category

class CategoryRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun fetchCategories(): List<Category> {
        val snapshot = firestore.collection("categories")
            .get()
            .await()

        return snapshot.documents.map { document ->
            Category(
                id = document.id,
                name = document.getString("name") ?: "",
                image = document.getString("image") ?: ""
            )
        }
    }
}
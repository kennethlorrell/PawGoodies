package com.deepdark.pawgoodies.repository

import com.deepdark.pawgoodies.data.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun fetchProducts(): List<Product> {
        val snapshot = firestore.collection("products")
            .get()
            .await()

        return snapshot.documents.map { document ->
            Product(
                id = document.id,
                name = document.getString("name") ?: "",
                price = document.getDouble("price") ?: 0.0,
                categoryId = document.getDocumentReference("categoryId"),
                animalId = document.getDocumentReference("animalId"),
                manufacturerId = document.getDocumentReference("manufacturerId"),
                breedId = document.getDocumentReference("breedId"),
                image = document.getString("image") ?: "",
            )
        }
    }
}
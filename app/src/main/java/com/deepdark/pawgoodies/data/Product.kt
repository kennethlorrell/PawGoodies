package com.deepdark.pawgoodies.data

import com.google.firebase.firestore.DocumentReference

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val image: String? = null,
    val categoryId: DocumentReference? = null,
    val animalId: DocumentReference? = null,
    val manufacturerId: DocumentReference? = null,
    val breedId: DocumentReference? = null
)

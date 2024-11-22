package com.deepdark.pawgoodies.data

import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID
import kotlin.random.Random

val db = FirebaseFirestore.getInstance()

fun seedFirestore() {
    val categories = listOf(
        mapOf("name" to "Корм"),
        mapOf("name" to "Ласощі"),
        mapOf("name" to "Догляд"),
        mapOf("name" to "Аксесуари"),
        mapOf("name" to "Іграшки")
    )

    val pets = listOf(
        mapOf("name" to "Собаки"),
        mapOf("name" to "Коти"),
        mapOf("name" to "Птахи"),
        mapOf("name" to "Гризуни")
    )

    val manufacturers = listOf(
        mapOf("name" to "Royal Canin"),
        mapOf("name" to "Purina"),
        mapOf("name" to "Hill's"),
        mapOf("name" to "Whiskas"),
        mapOf("name" to "Pedigree"),
        mapOf("name" to "Acana"),
        mapOf("name" to "Orijen"),
        mapOf("name" to "Tetra"),
        mapOf("name" to "Versele-Laga"),
        mapOf("name" to "Vitakraft")
    )

    val categoryProductTemplates = mapOf(
        "Корм" to listOf("Сухий корм", "Вологий корм", "Корм преміум-класу", "Гранули"),
        "Ласощі" to listOf("Палички", "Смаколики", "Сушене м'ясо", "Ласощі зі смаком курки"),
        "Догляд" to listOf("Шампунь", "Кондиціонер", "Спрей для догляду", "Засіб для чищення лап"),
        "Аксесуари" to listOf("Повідок", "Нашийник", "Миска", "Гребінець"),
        "Іграшки" to listOf("М'яч", "Канатик", "Плюшева іграшка", "Мишка для гри")
    )

    val categoryAdditionalInfo = mapOf(
        "Корм" to listOf("зі смаком курки", "зі смаком яловичини", "1кг", "3кг", "зі смаком лосося", "для чутливого травлення"),
        "Ласощі" to listOf("зі смаком риби", "з сиром", "30г", "150г", "для покращення травлення"),
        "Догляд" to listOf("200мл", "700мл", "для чутливої шкіри", "з натуральними компонентами", "без запаху"),
        "Аксесуари" to listOf("розмір S", "розмір M", "розмір L", "металевий", "пластиковий", "регульований"),
        "Іграшки" to listOf("великий", "малий", "м'який", "звуковий", "з мотузкою", "плюшевий")
    )


    val breeds = mapOf(
        "Собаки" to listOf(
            "Ротвейлер", "Німецька вівчарка", "Сенбернар", "Акіта", "Бульдог", "Доберман", "Шпіц", "Йоркширський тер'єр"
        ),
        "Коти" to listOf(
            "Перська", "Шотландська висловуха", "Британська короткошерста", "Сіамська", "Мейн-кун", "Бенгальська"
        )
    )

    val petNameFormats = mapOf(
        "Собаки" to "собак",
        "Коти" to "котів",
        "Гризуни" to "гризунів",
        "Птахи" to "птахів"
    )

    val categoryIds = categories.map { UUID.randomUUID().toString() to it["name"] }
    val petIds = pets.map { UUID.randomUUID().toString() to it["name"] }
    val manufacturerIds = manufacturers.map { UUID.randomUUID().toString() to it["name"] }
    val breedIds = mutableListOf<Pair<String, String>>()

    petIds.filter { it.second in breeds.keys }.forEach { (petId, petName) ->
        breeds[petName]?.forEach { breedName ->
            val breedId = UUID.randomUUID().toString()
            db.collection("breeds").document(breedId).set(
                mapOf("name" to breedName, "animalId" to db.document("pets/$petId"))
            ).addOnFailureListener { println("Failed to seed breed $breedName: ${it.message}") }
            breedIds.add(breedId to breedName)
        }
    }
    return

    categoryIds.forEach { (id, name) ->
        db.collection("categories").document(id).set(mapOf("name" to name))
    }

    petIds.forEach { (id, name) ->
        db.collection("pets").document(id).set(mapOf("name" to name))
    }

    manufacturerIds.forEach { (id, name) ->
        db.collection("manufacturers").document(id).set(mapOf("name" to name))
    }

    // Seed Products
    categoryIds.forEach { (categoryId, categoryName) ->
        petIds.forEach { (petId, petName) ->
            val formattedPetName = petNameFormats[petName] ?: petName ?: return@forEach

            (1..30).forEach { productIndex ->
                val manufacturer = manufacturerIds.random()
                val template = categoryProductTemplates[categoryName]?.random() ?: "Товар"
                val additionalInfo = categoryAdditionalInfo[categoryName]?.random() ?: ""

                val breedId = if (petName in breeds.keys && Random.nextBoolean() && breedIds.isNotEmpty()) {
                    breedIds.filter { breed ->
                        breeds[petName]?.contains(breed.second) == true // Check if the breed matches the pet type
                    }.randomOrNull()?.first // Safely pick a random entry, or return null if the list is empty
                } else null


                val productName = "$template для $formattedPetName ${manufacturer.second} $additionalInfo"
                val price = (20..2000).random()

                val product = mapOf(
                    "name" to productName,
                    "price" to price,
                    "categoryId" to db.document("categories/$categoryId"),
                    "animalId" to db.document("pets/$petId"),
                    "manufacturerId" to db.document("manufacturers/${manufacturer.first}"),
                    "breedId" to if (breedId != null) db.document("breeds/$breedId") else null
                )
                db.collection("products").add(product)
                    .addOnFailureListener { println("Failed to seed product $productName: ${it.message}") }
            }
        }
    }
}
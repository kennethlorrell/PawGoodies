package com.deepdark.pawgoodies.data.seeds

import com.deepdark.pawgoodies.data.AppDatabase
import com.deepdark.pawgoodies.data.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

fun seedDatabase(db: AppDatabase) {
    CoroutineScope(Dispatchers.IO).launch {
        insertManufacturers(db)
        insertAnimals(db)
        insertBreeds(db)
        insertCategories(db)
        insertProducts(db)
    }
}

private suspend fun insertManufacturers(db: AppDatabase) {
    val manufacturers = mapOf(
        "Food" to listOf("Royal Canin", "Purina", "Hill's", "Acana", "Orijen", "Whiskas", "Pedigree", "Farmina", "Brit", "Eukanuba"),
        "Grooming" to listOf("Tropiclean", "PetHead", "Bio-Groom", "Furminator", "Espree"),
        "Accessories" to listOf("Trixie", "K&H", "PetSafe", "Hunter", "Ferplast"),
        "Toys" to listOf("KONG", "Outward Hound", "Chuckit!", "West Paw", "Nerf Dog")
    )

    manufacturers.values.flatten().forEach { name ->
        db.manufacturerDao().insertManufacturer(Manufacturer(name = name))
    }
}

private suspend fun insertAnimals(db: AppDatabase) {
    val animals = listOf("Кіт", "Собака", "Гризун", "Птах")
    animals.forEach { name ->
        db.animalDao().insertAnimal(Animal(name = name))
    }
}

private suspend fun insertBreeds(db: AppDatabase) {
    val breedsByAnimal = mapOf(
        "Кіт" to listOf("Мейн-кун", "Сіамська", "Британська короткошерста", "Бенгальська", "Сфінкс"),
        "Собака" to listOf("Лабрадор", "Німецька вівчарка", "Бульдог", "Хаскі", "Бігль"),
        "Гризун" to listOf("Хом’як", "Морська свинка", "Шиншила", "Декоративний щур", "Декоративний кролик"),
        "Птах" to listOf("Хвилястий папуга", "Канарка", "Корела", "Неразлучник", "Амадина")
    )

    breedsByAnimal.forEach { (animal, breeds) ->
        val animalId = db.animalDao().getAnimalByName(animal)?.id ?: return@forEach
        breeds.forEach { breed ->
            db.breedDao().insertBreed(Breed(name = breed, animalId = animalId))
        }
    }
}

private suspend fun insertCategories(db: AppDatabase) {
    val categories = listOf("Корм", "Ласощі", "Догляд", "Аксесуари", "Іграшки")
    categories.forEach { name ->
        db.categoryDao().insertCategory(Category(name = name))
    }
}

private suspend fun insertProducts(db: AppDatabase) {
    val flavours = listOf(
        "з лососем", "з куркою", "з яловичиною",
        "з кроликом", "з індичкою", "з тунцем",
        "з качкою", "з ягням", "з креветками"
    )

    val groomingItems = listOf("Шампунь", "Спрей для догляду", "Гребінець", "Засіб для чищення лап", "Кондиціонер")
    val accessoryItems = listOf("Нашийник", "Повідок", "Миска", "Клітка", "Транспортна сумка")
    val categories = db.categoryDao().getAllCategories()

    categories.forEach { category ->
        val categoryId = category.id
        when (category.name) {
            "Корм" -> createFoodProducts(db, categoryId, flavours, "Корм")
            "Ласощі" -> createFoodProducts(db, categoryId, flavours, "Ласощі")
            "Догляд" -> createGeneralProducts(db, categoryId, groomingItems, "Grooming")
            "Аксесуари" -> createGeneralProducts(db, categoryId, accessoryItems, "Accessories")
            "Іграшки" -> createToyProducts(db, categoryId)
        }
    }
}

private suspend fun createFoodProducts(db: AppDatabase, categoryId: Int, flavours: List<String>, type: String) {
    val manufacturers = db.manufacturerDao().getAllManufacturers().filter { it.name in listOf(
        "Royal Canin", "Purina", "Hill's", "Acana", "Orijen", "Whiskas", "Pedigree", "Farmina", "Brit", "Eukanuba"
    ) }
    val animals = db.animalDao().getAllAnimals()

    repeat(12) {
        val manufacturer = manufacturers.random()
        val animal = animals.random()
        val formattedAnimal = formatAnimal(animal.name)
        val flavour = flavours.random()

        val productName = "$type ${manufacturer.name} для $formattedAnimal $flavour"
        db.productDao().insertProduct(
            Product(
                name = productName,
                price = Random.nextInt(50, 2000).toDouble(),
                categoryId = categoryId,
                manufacturerId = manufacturer.id,
                description = productName
            )
        )
    }
}

private suspend fun createGeneralProducts(db: AppDatabase, categoryId: Int, items: List<String>, manufacturerGroup: String) {
    val manufacturers = when (manufacturerGroup) {
        "Grooming" -> listOf("Tropiclean", "PetHead", "Bio-Groom", "Furminator", "Espree")
        "Accessories" -> listOf("Trixie", "K&H", "PetSafe", "Hunter", "Ferplast")
        else -> emptyList()
    }.mapNotNull { manufacturerName ->
        db.manufacturerDao().getManufacturerByName(manufacturerName)
    }

    if (manufacturers.isEmpty()) {
        throw IllegalStateException("No manufacturers found for group: $manufacturerGroup")
    }

    val animals = db.animalDao().getAllAnimals()

    repeat(12) {
        val item = items.random()
        val manufacturer = manufacturers.random()
        val animal = animals.random()
        val formattedAnimal = formatAnimal(animal.name)

        val productName = "$item ${manufacturer.name} для $formattedAnimal"
        db.productDao().insertProduct(
            Product(
                name = productName,
                price = Random.nextInt(50, 2000).toDouble(),
                categoryId = categoryId,
                manufacturerId = manufacturer.id,
                description = productName
            )
        )
    }
}

private suspend fun createToyProducts(db: AppDatabase, categoryId: Int) {
    val toyManufacturers = listOf("KONG", "Outward Hound", "Chuckit!", "West Paw", "Nerf Dog").mapNotNull { manufacturerName ->
        db.manufacturerDao().getManufacturerByName(manufacturerName)
    }

    if (toyManufacturers.isEmpty()) {
        throw IllegalStateException("No toy manufacturers found")
    }

    val animals = db.animalDao().getAllAnimals()

    repeat(12) {
        val manufacturer = toyManufacturers.random()
        val animal = animals.random()
        val formattedAnimal = formatAnimal(animal.name)

        val productName = "Іграшка для $formattedAnimal ${manufacturer.name}"
        db.productDao().insertProduct(
            Product(
                name = productName,
                price = Random.nextInt(50, 2000).toDouble(),
                categoryId = categoryId,
                manufacturerId = manufacturer.id,
                description = productName
            )
        )
    }
}

private fun formatAnimal(animal: String): String {
    return when (animal) {
        "Кіт" -> "котів"
        "Собака" -> "собак"
        "Гризун" -> "гризунів"
        "Птах" -> "птахів"
        else -> animal
    }
}

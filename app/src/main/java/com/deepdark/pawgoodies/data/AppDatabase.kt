package com.deepdark.pawgoodies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deepdark.pawgoodies.data.dao.AnimalDao
import com.deepdark.pawgoodies.data.dao.BreedDao
import com.deepdark.pawgoodies.data.dao.CartItemDao
import com.deepdark.pawgoodies.data.dao.CategoryDao
import com.deepdark.pawgoodies.data.dao.ManufacturerDao
import com.deepdark.pawgoodies.data.dao.PetDao
import com.deepdark.pawgoodies.data.dao.ProductDao
import com.deepdark.pawgoodies.data.dao.UserDao
import com.deepdark.pawgoodies.data.dao.WishlistItemDao
import com.deepdark.pawgoodies.data.entities.Animal
import com.deepdark.pawgoodies.data.entities.Breed
import com.deepdark.pawgoodies.data.entities.CartItem
import com.deepdark.pawgoodies.data.entities.Category
import com.deepdark.pawgoodies.data.entities.Manufacturer
import com.deepdark.pawgoodies.data.entities.Pet
import com.deepdark.pawgoodies.data.entities.Product
import com.deepdark.pawgoodies.data.entities.User
import com.deepdark.pawgoodies.data.entities.WishlistItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
    entities = [
        User::class,
        Category::class,
        Manufacturer::class,
        Breed::class,
        Pet::class,
        Product::class,
        Animal::class,
        CartItem::class,
        WishlistItem::class
    ],
    version = 2
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun manufacturerDao(): ManufacturerDao
    abstract fun breedDao(): BreedDao
    abstract fun petDao(): PetDao
    abstract fun productDao(): ProductDao
    abstract fun animalDao(): AnimalDao
    abstract fun cartItemDao(): CartItemDao
    abstract fun wishlistItemDao(): WishlistItemDao
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "pawgoodies_database").build()
    }
}

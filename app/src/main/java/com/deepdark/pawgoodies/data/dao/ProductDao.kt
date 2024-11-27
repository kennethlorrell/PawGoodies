package com.deepdark.pawgoodies.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(products: Product): Long

    @Insert
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT * FROM products")
    fun getAllProductsLive(): LiveData<List<Product>>

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductByIdLive(productId: Int): LiveData<Product?>

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    suspend fun getProductsByCategory(categoryId: Int): List<Product>

    @Query("SELECT * FROM products WHERE manufacturerId = :manufacturerId")
    suspend fun getProductsByManufacturer(manufacturerId: Int): List<Product>
}

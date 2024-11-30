package com.deepdark.pawgoodies.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deepdark.pawgoodies.data.entities.Product
import com.deepdark.pawgoodies.data.entities.stateful.ProductWithState

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

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductsCount(): Int

    @Query("""
        SELECT 
            p.id, 
            p.name, 
            p.price, 
            p.description, 
            p.image, 
            p.categoryId, 
            c.name AS categoryName, 
            p.manufacturerId, 
            m.name AS manufacturerName,
            EXISTS (
              SELECT 1
              FROM cart_items ci
              WHERE ci.productId = p.id
            ) AS isInCart,
            COALESCE((
              SELECT cart_items.quantity
              FROM cart_items
              WHERE cart_items.productId = p.id), 0
            ) AS cartQuantity,
              EXISTS (
              SELECT 1
              FROM wishlist_items wi
              WHERE wi.productId = p.id
            ) AS isInWishlist
        FROM products p
        INNER JOIN categories c ON p.categoryId = c.id
        INNER JOIN manufacturers m ON p.manufacturerId = m.id
        WHERE p.id = :productId
    """)
    fun getProductWithStateByIdLive(
        productId: Int
    ): LiveData<ProductWithState?>

    @Query("""
        SELECT 
            p.id, 
            p.name, 
            p.price, 
            p.description, 
            p.image, 
            p.categoryId, 
            c.name AS categoryName, 
            p.manufacturerId, 
            m.name AS manufacturerName,
            EXISTS (
              SELECT 1
              FROM cart_items ci
              WHERE ci.productId = p.id
            ) AS isInCart,
            EXISTS (
              SELECT 1
              FROM wishlist_items wi
              WHERE wi.productId = p.id
            ) AS isInWishlist,
            COALESCE((
              SELECT cart_items.quantity
              FROM cart_items
              WHERE cart_items.productId = p.id), 0
            ) AS cartQuantity
        FROM products p
        INNER JOIN categories c ON p.categoryId = c.id
        INNER JOIN manufacturers m ON p.manufacturerId = m.id
    """)
    fun getAllProductsWithStateLive(): LiveData<List<ProductWithState>>
}

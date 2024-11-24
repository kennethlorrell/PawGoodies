package com.deepdark.pawgoodies.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_0_1 = object : Migration(0, 1) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                email TEXT NOT NULL,
                password TEXT NOT NULL,
                name TEXT NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE categories (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE animals (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE manufacturers (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE breeds (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                animalId INTEGER NOT NULL,
                FOREIGN KEY(animalId) REFERENCES animals(id) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE pets (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userId INTEGER NOT NULL,
                breedId INTEGER NOT NULL,
                name TEXT NOT NULL,
                FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE,
                FOREIGN KEY(breedId) REFERENCES breeds(id) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE products (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                categoryId INTEGER NOT NULL,
                manufacturerId INTEGER NOT NULL,
                imageUrl TEXT,
                description TEXT,
                FOREIGN KEY(categoryId) REFERENCES categories(id) ON DELETE CASCADE,
                FOREIGN KEY(manufacturerId) REFERENCES manufacturers(id) ON DELETE CASCADE
            )
        """)
    }
}

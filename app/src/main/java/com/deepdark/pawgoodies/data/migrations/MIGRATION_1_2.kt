package com.deepdark.pawgoodies.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE cart_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userId INTEGER NOT NULL,
                productId INTEGER NOT NULL,
                quantity INTEGER NOT NULL,
                FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE,
                FOREIGN KEY(productId) REFERENCES products(id) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE wishlist_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userId INTEGER NOT NULL,
                productId INTEGER NOT NULL,
                FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE,
                FOREIGN KEY(productId) REFERENCES products(id) ON DELETE CASCADE
            )
        """)
    }
}
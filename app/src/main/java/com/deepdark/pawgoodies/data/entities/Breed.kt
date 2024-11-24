package com.deepdark.pawgoodies.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "breeds",
    foreignKeys = [
        ForeignKey(
            entity = Animal::class,
            parentColumns = ["id"],
            childColumns = ["animalId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Breed(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val animalId: Int,
    val name: String
)
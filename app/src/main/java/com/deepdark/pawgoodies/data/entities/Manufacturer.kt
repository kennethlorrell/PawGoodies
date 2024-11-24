package com.deepdark.pawgoodies.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manufacturers")
data class Manufacturer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
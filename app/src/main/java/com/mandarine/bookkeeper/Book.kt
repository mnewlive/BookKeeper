package com.mandarine.bookkeeper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
class Book(
    @PrimaryKey
    val id: String,

    val author: String,
    val book: String,
    val description: String
)
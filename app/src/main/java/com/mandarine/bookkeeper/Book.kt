package com.mandarine.bookkeeper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
class Book(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "author")
    val author: String,
    val book: String
) {
}
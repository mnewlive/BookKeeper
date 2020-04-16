package com.mandarine.bookkeeper

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BookDao {

    @Insert
    fun insert(book: Book)
}
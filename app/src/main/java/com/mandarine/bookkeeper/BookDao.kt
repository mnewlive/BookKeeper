package com.mandarine.bookkeeper

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao {

    @Insert
    fun insert(book: Book)

    //1st way to get data, as fun
//    @Query("SELECT * FROM books")
//    fun getAllBooks(): LiveData<List<Book>>

    //2nd way to get data, as propriety
    @get:Query("SELECT * FROM books")
    val allBooks: LiveData<List<Book>>

    @Update
    fun update(book: Book)
}
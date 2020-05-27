package com.mandarine.bookkeeper

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val allBooks: LiveData<List<Book>>
    private val bookRepository = BookRepository(application)

    init {
        allBooks = bookRepository.allBooks
    }

    fun insert(book: Book) {
        bookRepository.insert(book)
    }

    fun update(book: Book) {
        bookRepository.update(book)
    }

    fun delete(book: Book) {
        bookRepository.delete(book)
    }
}
package com.mandarine.bookkeeper

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BookViewModel(application: Application): AndroidViewModel(application) {

    val allBooks: LiveData<List<Book>>
    private val bookDao: BookDao

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        allBooks = bookDao.allBooks
    }

    fun insert(book: Book) {
        InsertAsyncTask(bookDao)
            .execute(book)
    }

    fun update(book: Book) {
        UpdateAsyncTask(bookDao)
            .execute(book)
    }

    fun delete(book: Book) {
        DeleteAsyncTask(bookDao)
            .execute(book)
    }

    companion object {
        private class InsertAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book): Void? {
                bookDao.insert(books[0])
                return null
            }
        }

        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book): Void? {
                bookDao.update(books[0])
                return null
            }
        }

        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book): Void? {
                bookDao.delete(books[0])
                return null
            }
        }
    }
}
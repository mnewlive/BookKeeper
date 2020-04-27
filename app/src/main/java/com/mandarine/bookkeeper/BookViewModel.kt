package com.mandarine.bookkeeper

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel

class BookViewModel(application: Application): AndroidViewModel(application) {

    private val bookDao: BookDao

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
    }

    fun insert(book: Book) {
        InsertAsyncTask(bookDao)
            .execute(book)
    }

    companion object {
        private class InsertAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>() {
            override fun doInBackground(vararg books: Book): Void? {
                bookDao.insert(books[0])
                return null
            }
        }
    }
}
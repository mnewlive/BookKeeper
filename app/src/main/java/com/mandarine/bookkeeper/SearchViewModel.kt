package com.mandarine.bookkeeper

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

//TODO: Optimize code with SearchResultActivity as MainActivity and BookViewModel(e.g. create a super class)
class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val bookDao: BookDao

    init {
        val bookDB = BookRoomDatabase.getDatabase(application)
        bookDao = bookDB!!.bookDao()
    }

	fun getBooksByBookOrAuthor(searchQuery: String): LiveData<List<Book>>? {
		return bookDao.getBooksByBookOrAuthor(searchQuery)
	}

    fun update(book: Book) {
        UpdateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book) {
        DeleteAsyncTask(bookDao).execute(book)
    }

    companion object {
        private class UpdateAsyncTask(private val bookDao: BookDao) :
            AsyncTask<Book, Void, Void>() {

            override fun doInBackground(vararg books: Book): Void? {
                bookDao.update(books[0])
                return null
            }
        }

        private class DeleteAsyncTask(private val bookDao: BookDao) :
            AsyncTask<Book, Void, Void>() {

            override fun doInBackground(vararg books: Book): Void? {
                bookDao.delete(books[0])
                return null
            }
        }
    }
}

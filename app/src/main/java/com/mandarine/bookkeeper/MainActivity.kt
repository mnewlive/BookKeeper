package com.mandarine.bookkeeper

import android.app.Activity
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BookListAdapter.OnDeleteClickListener {

    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val bookListAdapter = BookListAdapter(this, onDeleteClickListener = this)
        recyclerview?.adapter = bookListAdapter
        recyclerview?.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { view ->
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)
        }

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        viewModel.allBooks.observe(this, Observer { books ->
            bookListAdapter.setBooks(books)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val id = UUID.randomUUID().toString()
            val authorName = data?.getStringExtra(NewBookActivity.NEW_AUTHOR) ?: "Толстой"
            val bookName = data?.getStringExtra(NewBookActivity.NEW_BOOK) ?: "Война и мир"
            val description = data?.getStringExtra(NewBookActivity.NEW_BOOK) ?: "Жили были ..."

            val book = Book(id, authorName, bookName, description)
            viewModel.insert(book)
            Log.d("some", "all is ok")
        } else if (requestCode == UPDATED_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val id = data?.getStringExtra(EditBookActivity.ID) ?: ""
            val authorName = data?.getStringExtra(EditBookActivity.UPDATED_AUTHOR) ?: ""
            val bookName = data?.getStringExtra(EditBookActivity.UPDATED_BOOK) ?: ""
            val description = data?.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION) ?: ""

            val book = Book(id, authorName, bookName, description)
            viewModel.update(book)

        } else {
            Log.d("some", "Something was wrong")
        }
    }

    override fun onDeleteClickListener(book: Book) {
        viewModel.delete(book)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        // Setting the SearchResultActivity to show the result
        val componentName = ComponentName(this, SearchResultActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)

        return true
    }

    companion object {
        private const val NEW_BOOK_ACTIVITY_REQUEST_CODE = 1
        const val UPDATED_BOOK_ACTIVITY_REQUEST_CODE = 2
    }
}

package com.mandarine.bookkeeper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val bookListAdapter = BookListAdapter(this)
        recyclerview?.adapter = bookListAdapter
        recyclerview?.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { view ->
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE)
        }

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        viewModel.allBooks.observe(this, Observer { books ->
            bookListAdapter.setBooks(books)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val id = UUID.randomUUID().toString()
            val authorName = data?.getStringExtra(NewBookActivity.NEW_AUTHOR) ?: "Толстой"
            val bookName = data?.getStringExtra(NewBookActivity.NEW_BOOK) ?: "Война и мир"

            val book = Book(id, authorName, bookName)
            viewModel.insert(book)
            Log.d("some", "all is ok")
        } else {
            Log.d("some", "Something was wrong")
        }
    }

    companion object {
        private const val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1
        const val UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2
    }
}

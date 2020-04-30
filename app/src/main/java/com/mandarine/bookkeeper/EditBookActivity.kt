package com.mandarine.bookkeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new.*

class EditBookActivity : AppCompatActivity() {

    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        val bundle: Bundle? = intent.extras

        bundle?.let {
            id = bundle.getString("id")
            val author = bundle.getString("author")
            val book = bundle.getString("book")

            etAuthorName.setText(author)
            etBookName.setText(book)
        }


        bSave.setOnClickListener {
            finish()
        }

        bCancel.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ID = "book_id"
        const val UPDATED_AUTHOR = "author_name"
        const val UPDATED_BOOK = "book_name"
    }
}

package com.mandarine.bookkeeper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class BookListAdapter(
    private val context: Context,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private var bookList: List<Book> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.setData(book.book, book.lastUpdated, position)
        holder.setListeners()
    }

    override fun getItemCount(): Int = bookList.size

    fun setBooks(books: List<Book>) {
        bookList = books
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pos: Int = 0

        fun setData(book: String, lastUpdated: Date?, position: Int) {
            itemView.tvBook.text = book
            itemView.tvLastUpdated.text = getFormattedDate(lastUpdated)
            this.pos = position
        }

        fun setListeners() {
            itemView.setOnClickListener {
                val intent = Intent(context, EditBookActivity::class.java)
                intent.putExtra("id", bookList[pos].id)
                intent.putExtra("author", bookList[pos].author)
                intent.putExtra("book", bookList[pos].book)
                intent.putExtra("description", bookList[pos].description)
                intent.putExtra("lastUpdated", getFormattedDate(bookList[pos].lastUpdated))
                (context as Activity).startActivityForResult(
                    intent,
                    MainActivity.UPDATED_BOOK_ACTIVITY_REQUEST_CODE
                )
            }
            itemView.ivRowDelete.setOnClickListener {
				onDeleteClickListener.onDeleteClickListener(bookList[pos])
            }
        }

        private fun getFormattedDate(lastUpdated: Date?): String {
            var time = "Last Updated: "
            time += lastUpdated?.let {
                val sdf = SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
                sdf.format(lastUpdated)
            } ?: "Not Found"
            return time
        }
    }

    interface OnDeleteClickListener {
        fun onDeleteClickListener(book: Book)
    }
}

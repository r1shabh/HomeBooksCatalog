package com.example.home_books_catalog

import android.app.Application
import com.example.home_books_catalog.data.BookDatabase
import com.example.home_books_catalog.data.BookRepository

class BookCatalogApplication : Application() {

    val database by lazy { BookDatabase.getDatabase(this) }
    val repository by lazy { BookRepository(database.bookDao()) }
}
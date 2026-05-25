package com.example.home_books_catalog.data

import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {

    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun getBookByIsbn(isbn: String): Book? {
        return bookDao.getBookByIsbn(isbn)
    }

    suspend fun isBookAlreadySaved(isbn: String): Boolean {
        return bookDao.getBookByIsbn(isbn) != null
    }
}
package com.example.home_books_catalog.network

import com.example.home_books_catalog.data.Book

object BookLookupService {

    suspend fun lookupByIsbn(isbn: String): Book? {
        return tryOpenLibrary(isbn) ?: tryGoogleBooks(isbn)
    }

    private suspend fun tryOpenLibrary(isbn: String): Book? {
        return try {
            val key = "ISBN:$isbn"
            val response = RetrofitInstance.api.getBookByIsbn(bibkeys = key)
            val bookData = response[key] ?: return null
            val title = bookData.title ?: return null
            val authors = bookData.authors
                ?.mapNotNull { it.name }
                ?.joinToString(", ")
                ?: "Unknown Author"

            Book(
                isbn = isbn,
                title = title,
                authors = authors,
                publishYear = bookData.publishDate,
                coverUrl = bookData.cover?.medium
            )
        } catch (e: Exception) {
            android.util.Log.e("BookLookup", "OpenLibrary failed: ${e.message}")
            null
        }
    }

    private suspend fun tryGoogleBooks(isbn: String): Book? {
        return try {
            val response = GoogleBooksRetrofitInstance.api.getBookByIsbn("isbn:$isbn")
            val item = response.items?.firstOrNull() ?: return null
            val info = item.volumeInfo

            val title = info.title ?: return null
            val authors = info.authors?.joinToString(", ") ?: "Unknown Author"
            val coverUrl = info.imageLinks?.thumbnail
                ?.replace("http://", "https://")

            Book(
                isbn = isbn,
                title = title,
                authors = authors,
                publishYear = info.publishedDate?.take(4),
                coverUrl = coverUrl
            )
        } catch (e: Exception) {
            android.util.Log.e("BookLookup", "GoogleBooks failed: ${e.message}")
            null
        }
    }
}
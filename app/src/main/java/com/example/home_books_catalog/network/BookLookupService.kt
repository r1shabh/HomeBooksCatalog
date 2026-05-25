package com.example.home_books_catalog.network

import com.example.home_books_catalog.data.Book

object BookLookupService {

    suspend fun lookupByIsbn(isbn: String): Book? {
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
            android.util.Log.e("BookLookup", "Error: ${e.message}", e)
            null
        }
    }
}
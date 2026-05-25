package com.example.home_books_catalog.network

import com.example.home_books_catalog.data.Book

object BookLookupService {

    suspend fun lookupByIsbn(isbn: String): Book? {
        return try {
            val response = RetrofitInstance.api.getBookByIsbn("ISBN:$isbn")

            val title = response.title ?: return null
            val authors = response.authors
                ?.mapNotNull { it.key?.removePrefix("/authors/") }
                ?.joinToString(", ")
                ?: "Unknown Author"
            val coverUrl = response.covers
                ?.firstOrNull()
                ?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }

            Book(
                isbn = isbn,
                title = title,
                authors = authors,
                publishYear = response.publishDate,
                coverUrl = coverUrl
            )
        } catch (e: Exception) {
            null
        }
    }
}
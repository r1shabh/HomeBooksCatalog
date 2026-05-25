package com.example.home_books_catalog.network

import retrofit2.http.GET
import retrofit2.http.Path

interface OpenLibraryApi {

    @GET("books/{isbn}.json")
    suspend fun getBookByIsbn(@Path("isbn") isbn: String): OpenLibraryResponse
}
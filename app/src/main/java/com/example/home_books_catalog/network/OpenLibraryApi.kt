package com.example.home_books_catalog.network

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {

    @GET("api/books")
    suspend fun getBookByIsbn(
        @Query("bibkeys") bibkeys: String,
        @Query("format") format: String = "json",
        @Query("jscmd") jscmd: String = "data"
    ): Map<String, BookData>
}
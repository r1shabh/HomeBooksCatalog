package com.example.home_books_catalog.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleBooksRetrofitInstance {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    val api: GoogleBooksApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApi::class.java)
    }
}
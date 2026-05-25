package com.example.home_books_catalog.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun getBookByIsbn(@Query("q") query: String): GoogleBooksResponse
}

data class GoogleBooksResponse(
    @SerializedName("items") val items: List<GoogleBookItem>?
)

data class GoogleBookItem(
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    @SerializedName("title") val title: String?,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("imageLinks") val imageLinks: ImageLinks?
)

data class ImageLinks(
    @SerializedName("thumbnail") val thumbnail: String?
)
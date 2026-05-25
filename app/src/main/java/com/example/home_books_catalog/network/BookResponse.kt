package com.example.home_books_catalog.network

import com.google.gson.annotations.SerializedName

data class OpenLibraryResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("authors") val authors: List<AuthorEntry>?,
    @SerializedName("publish_date") val publishDate: String?,
    @SerializedName("covers") val covers: List<Int>?
)

data class AuthorEntry(
    @SerializedName("key") val key: String?
)

data class BookData(
    @SerializedName("title") val title: String?,
    @SerializedName("authors") val authors: List<AuthorData>?,
    @SerializedName("publish_date") val publishDate: String?,
    @SerializedName("cover") val cover: CoverData?
)

data class AuthorData(
    @SerializedName("name") val name: String?
)

data class CoverData(
    @SerializedName("medium") val medium: String?
)
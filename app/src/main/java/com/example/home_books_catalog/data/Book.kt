package com.example.home_books_catalog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey val isbn: String,
    val title: String,
    val authors: String,
    val publishYear: String?,
    val coverUrl: String?,
    val addedAt: Long = System.currentTimeMillis()
)
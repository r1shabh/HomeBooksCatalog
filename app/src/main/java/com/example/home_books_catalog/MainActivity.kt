package com.example.home_books_catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.home_books_catalog.ui.booklist.BookListScreen
import com.example.home_books_catalog.ui.theme.HomeBooksCatalogTheme
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.home_books_catalog.network.BookLookupService
import com.example.home_books_catalog.network.RetrofitInstance

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeBooksCatalogTheme {
                BookListScreen()
            }
        }
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getBookByIsbn("ISBN:9780743273565")
                android.util.Log.d("BookTest", "Response: $response")
                val book = BookLookupService.lookupByIsbn("9780743273565")
                android.util.Log.d("BookTest", "Book: $book")
                book?.let {
                    (applicationContext as BookCatalogApplication).repository.insertBook(it)
                }
            } catch (e: Exception) {
                android.util.Log.e("BookTest", "Error: ${e.message}", e)
            }
        }
    }
}
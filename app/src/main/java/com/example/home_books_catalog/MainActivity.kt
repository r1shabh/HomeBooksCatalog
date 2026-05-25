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
    }
}
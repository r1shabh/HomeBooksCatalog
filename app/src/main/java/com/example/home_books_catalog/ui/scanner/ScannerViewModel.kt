package com.example.home_books_catalog.ui.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.home_books_catalog.data.BookRepository
import com.example.home_books_catalog.network.BookLookupService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ScanState {
    object Idle : ScanState()
    object Loading : ScanState()
    data class Success(val bookTitle: String) : ScanState()
    data class AlreadySaved(val bookTitle: String) : ScanState()
    object NotFound : ScanState()
    data class Error(val message: String) : ScanState()
}

class ScannerViewModel(private val repository: BookRepository) : ViewModel() {

    private val _scanState = MutableStateFlow<ScanState>(ScanState.Idle)
    val scanState: StateFlow<ScanState> = _scanState

    // Prevents processing multiple barcodes simultaneously
    private var isProcessing = false

    fun onBarcodeDetected(barcode: String) {
        if (isProcessing) return
        isProcessing = true
        _scanState.value = ScanState.Loading

        viewModelScope.launch {
            try {
                if (repository.isBookAlreadySaved(barcode)) {
                    val existingBook = repository.getBookByIsbn(barcode)
                    _scanState.value = ScanState.AlreadySaved(existingBook?.title ?: barcode)
                    resetAfterDelay()
                    return@launch
                }

                val book = BookLookupService.lookupByIsbn(barcode)
                if (book != null) {
                    repository.insertBook(book)
                    _scanState.value = ScanState.Success(book.title)
                } else {
                    _scanState.value = ScanState.NotFound
                }
            } catch (e: Exception) {
                _scanState.value = ScanState.Error(e.message ?: "Unknown error")
            } finally {
                resetAfterDelay()
            }
        }
    }

    private suspend fun resetAfterDelay() {
        kotlinx.coroutines.delay(3000)
        _scanState.value = ScanState.Idle
        isProcessing = false
    }

    class Factory(private val repository: BookRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ScannerViewModel(repository) as T
        }
    }
}
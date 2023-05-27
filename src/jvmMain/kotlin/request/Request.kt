package request

import androidx.compose.runtime.MutableState
import com.google.gson.Gson
import models.Book
import models.GoogleBooksResponse
import java.net.URL

fun request(books: MutableState<List<Book>>, text: MutableState<String>? = null) {
    val json: String = if (text?.value.isNullOrEmpty()) {
        URL("https://www.googleapis.com/books/v1/volumes?q=book&maxResults=30").readText()
    } else {
        URL("https://www.googleapis.com/books/v1/volumes?q=${text?.value?.trim()}&maxResults=30").readText()
    }
    val response = Gson().fromJson(json, GoogleBooksResponse::class.java)
    books.value = response.items.mapNotNull { item ->
        try {
            val volumeInfo = item.volumeInfo
            Book(
                id = item.id,
                title = volumeInfo.title,
                imageUrl = volumeInfo.imageLinks?.thumbnail ?: "",
                bookUrl = item.previewLink?.previewLink ?: "",

                )
        } catch (e: Exception) {
            null
        }
    }
}
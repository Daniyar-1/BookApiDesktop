package screens.utils

import models.Book
import java.awt.Desktop
import java.net.URI

fun SearchBookId(book: Book) {
    val desktop = Desktop.getDesktop()
    when {
        Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE) -> desktop.browse(URI("http://books.google.kg/books?id=${book.id}&printsec=frontcover&dq=book&hl=&cd=1&source=gbs_api"))
        else -> throw RuntimeException("cannot open")
    }
}
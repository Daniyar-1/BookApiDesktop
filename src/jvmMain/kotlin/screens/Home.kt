package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Book
import request.request
import screens.utils.AsyncImage
import screens.utils.SearchBookId
import screens.utils.loadImageBitmap
import screens.utils.searchBar

@Composable
fun Home(isOnProgress: MutableState<Boolean>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val listOfBooks = remember { mutableStateOf(emptyList<Book>()) }

        val searchText = remember { mutableStateOf("") }

        request(listOfBooks)

        Column(modifier = Modifier.fillMaxSize().padding(all = 32.dp)) {
            Text("BookApp", fontSize = 28.sp)
            searchBar(listOfBooks, searchText, isOnProgress)
            if (!isOnProgress.value) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(200.dp),
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier.padding(top = 24.dp),
                ) {
                    itemsIndexed(listOfBooks.value) { _, item ->
                        BookItem(book = item)
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource("drawable/loading.jpeg"), contentDescription = "Loading")
                }
            }
        }
    }
}

@Composable
fun BookItem(
    book: Book,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 24.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .clickable {
                SearchBookId(book)
            }
    ) {
        Card(
            backgroundColor = Color.White,
            elevation = 1.dp
        ) {
            if (book.imageUrl.isNotEmpty()) {
                AsyncImage(
                    load = {
                        loadImageBitmap(
                            book.imageUrl.replace("http", "https")
                        )
                    },
                    painterFor = { BitmapPainter(it) },
                    contentDescription = "Book",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(200.dp)
                )
            } else if (book.imageUrl.isEmpty()) {
                Image(painter = painterResource("drawable/no_image.jpeg"), contentDescription = "No image")
            }
        }

        Text(
            text = book.title,
            color = Color.Black,
            fontFamily = FontFamily(Font("font/sfprotext_bold.ttf")),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 2.dp, top = 8.dp)
        )
        Divider(
            color = Color.Gray, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
        )
    }
}
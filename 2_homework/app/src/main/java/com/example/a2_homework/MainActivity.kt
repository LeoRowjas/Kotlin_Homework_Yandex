package com.example.a2_homework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlin.random.Random

data class Picture(
    val id: Int,
    val author: String,
    val url: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                GalleryApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryApp() {
    var searchText by remember { mutableStateOf("") }
    var isGridView by remember { mutableStateOf(false) }
    var gallery by remember { mutableStateOf(generateSamplePictures()) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Поиск по автору...") },
                    singleLine = true
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { isGridView = !isGridView }) {
                        Icon(
                            if (isGridView) Icons.Default.List else Icons.Default.Menu,
                            contentDescription = "Переключить вид"
                        )
                    }
                    IconButton(onClick = { gallery = emptyList() }) {
                        Icon(Icons.Default.Clear, contentDescription = "Очистить все")
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val newPicture = generateNewPicture()
                    if (gallery.none { it.id == newPicture.id || it.url == newPicture.url }) {
                        gallery = gallery + newPicture
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить картинку")
            }
        }
    ) { padding ->
        val filteredGallery = gallery.filter {
            it.author.contains(searchText, ignoreCase = true)
        }

        if (isGridView) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(filteredGallery) { picture ->
                    PictureItem(
                        picture = picture,
                        onPictureClick = { gallery = gallery - picture }
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(filteredGallery) { picture ->
                    PictureItem(
                        picture = picture,
                        onPictureClick = { gallery = gallery - picture }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureItem(picture: Picture, onPictureClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onPictureClick)
    ) {
        Column {
            GlideImage(
                model = picture.url,
                contentDescription = "Картинка от ${picture.author}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = picture.author,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun generateSamplePictures(): List<Picture> {
    return listOf(
        Picture(1, "Иван Иванов", "https://picsum.photos/seed/rowjas/400"),
        Picture(2, "Мария Петрова", "https://picsum.photos/seed/kakashki/400"),
        Picture(3, "Алексей Сидоров", "https://picsum.photos/seed/popa/400"),
        Picture(4, "Елена Козлова", "https://picsum.photos/seed/mishaloh/400"),
        Picture(5, "Дмитрий Волков", "https://picsum.photos/seed/batman/400")
    )
}

private fun generateNewPicture(): Picture {
    val id = Random.nextInt(6, 1000)
    val authors = listOf(
        "Анна Соколова",
        "Павел Морозов",
        "София Михайлова",
        "Артем Королев",
        "Виктория Новикова"
    )
    return Picture(
        id = id,
        author = authors.random(),
        url = "https://picsum.photos/seed/$id/400"
    )
}

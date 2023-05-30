package com.nicolascastilla.challenge.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nicolascastilla.challenge.activities.Greeting
import com.nicolascastilla.challenge.activities.ui.theme.ChallengeTheme
import com.nicolascastilla.challenge.compose.utils.RemoteImageFull
import com.nicolascastilla.challenge.ui.theme.CustomBlack
import com.nicolascastilla.challenge.ui.theme.CustomBlue
import com.nicolascastilla.challenge.ui.theme.CustomGrey
import com.nicolascastilla.challenge.viewmodels.MainViewModel
import com.nicolascastilla.entities.Song

@Composable
fun ScreenFavorites(navController: NavHostController?, viewModel: MainViewModel){
    val favouriteList by viewModel.favoritesList.collectAsState(emptyList())

    ChallengeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomBlack
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.Red)
                ) {
                    TopAppBar(
                        title = { Text("Favourites", textAlign = TextAlign.Center) },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController?.popBackStack()
                            }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                            }
                        },
                        actions = {},
                        backgroundColor = CustomBlue,
                        contentColor = Color.White,
                        elevation = 12.dp
                    )
                }
                LazyVerticalGrid(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ){

                    items(favouriteList.size) { pos ->
                        RowFavourites(favouriteList.get(pos),viewModel,favouriteList)
                    }

                }
            }
        }
    }
}

@Composable
fun RowFavourites(item: Song,viewModel: MainViewModel,list: List<Song>){
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp)
                .clickable {
                    viewModel.updateCurrentSong(item, list)
                    viewModel.setViewVisibility(true)
                    if (viewModel.isPlaying.value) {
                        viewModel.stop()
                    }
                    viewModel.initMediaPlayer(item)
                }
        ) {
            RemoteImageFull(item.album.cover_medium)
        }
        Spacer(modifier = Modifier.width(5.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(end = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 30.dp)
            ) {
                Text(
                    text = item.title,
                    color = Color(0xFFFCFCFC),
                    style = TextStyle(fontSize = 16.sp, fontWeight= FontWeight.Bold)
                )
                Text(
                    text = item.artist.name,
                    color = CustomGrey,
                    style = TextStyle(fontSize = 14.sp)
                )
            }
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color.Green,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        viewModel.removeFavourite(item)
                    }
            )
        }

    }

}


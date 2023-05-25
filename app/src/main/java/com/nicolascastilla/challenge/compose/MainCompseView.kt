@file:OptIn(ExperimentalMaterial3Api::class)

package com.nicolascastilla.challenge.compose

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascastilla.challenge.compose.utils.ChallengeButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.nicolascastilla.entities.Song
import kotlinx.coroutines.launch
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge.compose.utils.RemoteImageFull
import com.nicolascastilla.challenge.ui.theme.*
import com.nicolascastilla.challenge.viewmodels.MainViewModel


@Composable
fun MainView() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context: Context = LocalContext.current
    val modifier: Modifier = Modifier
    val scaffolState = rememberScaffoldState()
    val coroutineScope= rememberCoroutineScope()
    val viewModel = viewModel<MainViewModel>()
    val isTopViewVisible by viewModel.isTopViewVisible.collectAsState()

    Scaffold(
        scaffoldState = scaffolState,
        topBar = { TopBar(scaffolState) },
        backgroundColor = CustomBlack,
        drawerContent = {
            Column {
                Text("Item 1", modifier = Modifier.clickable { /* Handle click */ })
                Text("Item 2", modifier = Modifier.clickable { /* Handle click */ })
                // Agrega más elementos aquí
            }
        },
        drawerGesturesEnabled = true,
        drawerContentColor = MaterialTheme.colors.onSurface,
        drawerBackgroundColor = MaterialTheme.colors.surface,
        drawerScrimColor = Color.Transparent,

    ) {
        modifier.padding(it)
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Yellow)
                .fillMaxWidth()
        ) {
            Column {
                TrendingNow(viewModel)
                CustomList()
            }
        }

        MainExpandableBottomView(viewModel)

    }

}


@Composable
fun TopBar(scaffoldState: ScaffoldState) {
    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ChallengeButton(
            icon = Icons.Default.Menu,
            function = {
                scope.launch { scaffoldState.drawerState.open() }
            }
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Search", color = Color.White,style = TextStyle(fontSize = 14.sp)) },
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.White,

                )},
            colors = TextFieldDefaults.colors(
                contentColorFor(backgroundColor = Color(0x26fcfcff)),
                unfocusedContainerColor = ElemetBackgound,
                disabledTextColor = Color.White,
                focusedContainerColor = ElemetBackgound,
                cursorColor = Color.White,
            ),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
    }
}

fun tempListSongs():List<Song>{
    return emptyList()
}

@Composable
fun TrendingNow(viewModel: MainViewModel) {
    val isLoading by viewModel.isLoadins.collectAsState(initial = true)
    val trendings by viewModel.myTrendings.collectAsState(emptyList())
    viewModel.getTrendings()
    Column {
        Text(
            text = "Trending right now",
            color = Color(0xFFFCFCFC),
            style = MaterialTheme.typography.h5
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(Color.Green)
        ){
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else {
                LazyRow(
                    modifier = Modifier .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(trendings) { song ->
                        RowTrending(song,viewModel)
                    }
                }
            }
        }

        LazyRow() {
            items(listOf("Rock", "Hip-pop", "etc")) { genre ->
                Button(onClick = { /*TODO*/ }) {
                    Text(genre)
                }
            }
        }
    }
}

@Composable
fun RowTrending(song: Song,viewModel: MainViewModel){
   val context = LocalContext.current

    Box(
        modifier = Modifier
            .width(260.dp)
            .fillMaxHeight()
            .padding(10.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
           // .padding(bottom = 10.dp)
    ) {
        // Imagen de fondo
        RemoteImageFull(song.artist.picture_big)

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (imgMore, infoZone) = createRefs()

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { /* Acción al hacer clic en la imagen */ }
                    .constrainAs(imgMore) {
                        top.linkTo(parent.top, margin = 1.dp)
                        end.linkTo(parent.end, margin = 10.dp)

                    }

            ){
                Text("...",
                    color = Color.White,
                    style = TextStyle(fontSize = 25.sp, fontWeight= FontWeight.Bold)
                )
            }

            Box(
                modifier = Modifier
                    .height(60.dp)
                    .padding(5.dp)
                    .clickable {
                        viewModel.updateCurrentSong(song)
                        viewModel.setViewVisibility(true)
                    }
                    .fillMaxWidth(0.9f)
                    .background(BluePalid, shape = RoundedCornerShape(16.dp))
                    .constrainAs(infoZone) {
                        bottom.linkTo(parent.bottom, margin = 5.dp)
                        end.linkTo(parent.end, margin = 10.dp)
                        start.linkTo(parent.start, margin = 10.dp)

                    }

            ) {
               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   modifier = Modifier
                       .fillMaxHeight()
                       .padding(start = 5.dp)
               ){
                   Column(
                       modifier = Modifier
                           .fillMaxWidth(0.8f)
                           .padding(start = 5.dp)
                   ) {
                      Text(
                          text= song.title,
                          color = Color(0xFFFCFCFC),
                          style = TextStyle(fontSize = 12.sp, fontWeight= FontWeight.Bold)
                      )
                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                       ){
                           Image(
                               painter = painterResource(id = com.nicolascastilla.challenge.R.drawable.music_icon),
                               contentDescription = "Music",
                               modifier = Modifier.size(13.dp),
                           )
                           Text(
                               text=" ${song.artist.name} ",
                               color = Color(0xFFFCFCFC),
                               style = TextStyle(fontSize = 10.sp)
                           )
                       }

                   }
                   Image(
                       painter = painterResource(id = com.nicolascastilla.challenge.R.drawable.play_d),
                       contentDescription = "Play",
                       modifier = Modifier.size(48.dp),
                   )
               }
            }
        }



    }
}

@Composable
fun CustomList() {
    LazyColumn {
        items(listOf("Item 1", "Item 2", "Item 3")) { item ->
            ListItem(item)
        }
    }
}

@Composable
fun ListItem(item: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(com.nicolascastilla.challenge.R.drawable.ic_launcher_background),
            contentDescription = null
        )
        Column {
            Text(
                text = "Title",
                color = Color(0xFFFCFCFC)
            )
            Text(
                text = "Artist",
                color = Color(0xFFA2AE37)
            )
        }
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = Color.Red
        )
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicScreen() {
    Surface(color = Color(0xFF2D2E37)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Row {
                        Button(
                            onClick = { /*TODO: Handle click*/ },
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFFC4C4C4))
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                        TextField(
                            value = "",
                            onValueChange = {},
                            placeholder = { Text("Search") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier
                    .background(Color(0xFF2D2E37))

            )
            Text(
                text = "Trending right now",
                color = Color(0xFFFCFCFC),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )
            HorizontalScrollSection()
            LazyColumn {
                items(getSongs()) { song ->
                    SongRow(song = song)
                }
            }
        }
    }
}
@Composable
fun HorizontalScrollSection() {
    // Replace with your actual categories
    val categories = listOf("Rock", "Hip Hop", "Pop", "Jazz", "Classical")
    LazyRow {
        items(categories) { category ->
            Button(onClick = { /*TODO: Handle click*/ }) {
                Text(text = category)
            }
        }
    }
}

@Composable
fun SongRow(song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(com.nicolascastilla.challenge.R.drawable.ic_launcher_background), // Replace with your actual image resource
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = song.title, color = Color(0xFFFCFCFC), style = MaterialTheme.typography.h6)
            Text(text = song.artist, color = Color(0xFFA2AE37), style = MaterialTheme.typography.h1)
        }
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = null,
            tint = Color.White
        )
    }
}
*/


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainView()
}

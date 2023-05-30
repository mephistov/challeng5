@file:OptIn(ExperimentalMaterial3Api::class)

package com.nicolascastilla.challenge.compose

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
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
    val modifier: Modifier = Modifier
    val scaffolState = rememberScaffoldState()
    val viewModel = viewModel<MainViewModel>()
    val isPLaying by viewModel.isPlaying.collectAsState(initial = false)

    val isLoadingGenere by viewModel.isLoadingGenere.collectAsState(initial = true)
    viewModel.getGenereSearch("Pop")

    Scaffold(
        scaffoldState = scaffolState,
       // topBar = { TopBar(scaffolState) },
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
                .background(CustomBlack)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
            ) {
                Column {
                    TopBar(scaffolState)
                    Spacer(modifier = Modifier.height(20.dp))
                    TrendingNow(viewModel)
                    GeneroMusic(viewModel,isLoadingGenere)
                }
            }

            if(isPLaying) {
                Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                        ) {
                    MiniPlayerView(viewModel)
                }
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
                //.background(Color.Green)
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
                        RowTrending(song,trendings,viewModel)
                    }
                }
            }
        }

        
    }
}

@Composable
fun GeneroMusic(viewModel: MainViewModel, isLoadingGenere: Boolean){

    Column(){
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(listOf("Pop", "Rap/Hip Hop", "Rock", "Dance", "Electro", "Alternative", "Reggae", "Reggaeton", "Jazz", "Blues", "Classical", "Films/Games", "African", "Arabic", "Indian", "Kids", "Latin", "Turkish", "Asian", "Oldies", "Folk", "Soul & Funk", "Punk")
            ) { genre ->
                Button(onClick = {  viewModel.getGenereSearch(genre)  }) {
                    Text(genre)
                }
            }
        }
        Box(
                modifier = Modifier
                    .fillMaxSize()

                ) {
            Spacer(modifier = Modifier.height(5.dp))
            if(isLoadingGenere){
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }else{
                GenereList(viewModel)
            }
        }
    }


    
}

@Composable
fun RowTrending(song: Song, listSongs: List<Song>, viewModel: MainViewModel){

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
                        viewModel.updateCurrentSong(song, listSongs)
                        viewModel.setViewVisibility(true)
                        if (viewModel.isPlaying.value) {
                            viewModel.stop()
                        }
                        viewModel.initMediaPlayer(song)
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
fun GenereList(viewModel: MainViewModel) {
    val genereList by viewModel.genereList.collectAsState(emptyList())
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(genereList) { item ->
            ListItem(item,viewModel,genereList)
        }
    }
}

@Composable
fun ListItem(item: Song,viewModel: MainViewModel,listSongs: List<Song>) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                viewModel.updateCurrentSong(item, listSongs)
                viewModel.setViewVisibility(true)
                if (viewModel.isPlaying.value) {
                    viewModel.stop()
                }
                viewModel.initMediaPlayer(item)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),

            )
            {
               //TODO imagen de internet
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                verticalArrangement = Arrangement.Center
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
        }
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
        )
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}

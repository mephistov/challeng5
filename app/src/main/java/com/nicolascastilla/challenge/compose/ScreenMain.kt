package com.nicolascastilla.challenge.compose

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nicolascastilla.challenge.R
import com.nicolascastilla.challenge.compose.utils.ChallengeButton
import com.nicolascastilla.challenge.compose.utils.RemoteImageFull
import com.nicolascastilla.challenge.ui.theme.BluePalid
import com.nicolascastilla.challenge.ui.theme.CustomGrey
import com.nicolascastilla.challenge.ui.theme.ElemetBackgound
import com.nicolascastilla.challenge.viewmodels.MainViewModel
import com.nicolascastilla.entities.Song
import kotlinx.coroutines.launch

@Composable
fun Screen1MainView(navController: NavHostController, scaffolState: ScaffoldState,viewModel: MainViewModel){

    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
        Column {
            TopBar(scaffolState,viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            TrendingNow(viewModel)
            GeneroMusic(viewModel)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopBar(scaffoldState: ScaffoldState,viewModel: MainViewModel) {
    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.searchByName(text)
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.White,

                    )
            },
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
    //val trendings by viewModel.myTrendings.collectAsState(emptyList())
    //val isLoading by viewModel.isLoadins.collectAsState(initial = true)
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
            if (viewModel.isLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else {
                LazyRow(
                    modifier = Modifier .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(viewModel.myTrendings.value) { song ->
                        RowTrending(song,viewModel.myTrendings.value,viewModel)
                    }
                }
            }
        }


    }
}

@Composable
fun GeneroMusic(viewModel: MainViewModel){
    val isLoadingGenere by viewModel.isLoadingGenere.collectAsState(initial = true)
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
                    .clickable {
                        viewModel.setFavorite(song)
                    }
                    .constrainAs(imgMore) {
                        top.linkTo(parent.top, margin = 1.dp)
                        end.linkTo(parent.end, margin = 10.dp)

                    }

            ){
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = if(song.isFavorite) Color.Green else Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)

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
                                painter = painterResource(id = R.drawable.music_icon),
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
                        painter = painterResource(id = R.drawable.play_d),
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
fun ListItem(item: Song, viewModel: MainViewModel, listSongs: List<Song>) {

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
                RemoteImageFull(item.album.cover_medium)
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
            tint = if(item.isFavorite) Color.Green else Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    viewModel.setFavorite(item)
                }
        )
    }

}
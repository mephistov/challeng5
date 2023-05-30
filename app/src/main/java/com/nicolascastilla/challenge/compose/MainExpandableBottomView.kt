package com.nicolascastilla.challenge.compose

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nicolascastilla.challenge.ui.theme.CustomBlack
import com.nicolascastilla.challenge.viewmodels.MainViewModel
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.nicolascastilla.challenge.compose.utils.RemoteImageFull
import com.nicolascastilla.challenge.compose.utils.playerFormat
import com.nicolascastilla.challenge.ui.theme.CustomGrey


@Composable
fun MainExpandableBottomView(viewModel: MainViewModel) {
    val isTopViewVisible by viewModel.isTopViewVisible.collectAsState()
    AnimatedVisibility(
        visible = isTopViewVisible,
        enter = slideInVertically(initialOffsetY = { it }),  // Entrada animada desde la parte inferior
        exit = slideOutVertically(targetOffsetY = { it })  // Salida animada desde la parte superior
    ) {
        // Aquí va el contenido de la vista superior
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomBlack)

        ) {

            PlayerView(viewModel)
        }
    }
}

@Composable
fun PlayerView(viewModel: MainViewModel) {
    val isPLaying by viewModel.isPlaying.collectAsState(initial = true)
    val maxSizeSong by viewModel.maxSizeSong.collectAsState(initial = 30.0f)
    val currentSongPosition by viewModel.currentSongPosition.collectAsState(initial = 0.0f)
    val currentTitle by viewModel.currentTitle.collectAsState(initial = "")
    val currentArtist by viewModel.currentArtist.collectAsState(initial = "")
    val artistImg by viewModel.img1.collectAsState(initial = "")
    val albumImg by viewModel.img2.collectAsState(initial = "")
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (closeZone, infoZone,controlZone) = createRefs()
        
        Box(
            modifier = Modifier
                .height(50.dp)
                .background(Color.Transparent)
                .fillMaxWidth()
                .clickable { viewModel.setViewVisibility(false) }
                .constrainAs(closeZone) {
                    top.linkTo(parent.top, margin = 1.dp)
                }
        ) {
            Box(
                modifier = Modifier
                    .background(CustomGrey)
                    .height(4.dp)
                    .width(60.dp)
                    .align(Alignment.Center)

            )
        }
        Box (
            modifier = Modifier
                .background(CustomBlack)
                .fillMaxWidth()
                .constrainAs(infoZone) {
                    top.linkTo(closeZone.bottom)
                    bottom.linkTo(controlZone.top)
                    height = Dimension.fillToConstraints
                }
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.8f)
                    .padding(10.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .align(alignment = Alignment.Center)
                // .padding(bottom = 10.dp)
            )
            {
                // Imagen de fondo
                val urlIMge = artistImg
                RemoteImageFull(urlIMge)
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .align(alignment = Alignment.Center)
                ) {
                    RemoteImageFull(albumImg)
                }
            }
        }

        Box (
            modifier = Modifier
                .background(CustomBlack)
                .fillMaxWidth()

                .constrainAs(controlZone) {
                    bottom.linkTo(parent.bottom, margin = 1.dp)
                }
                )
        {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = currentTitle,
                    color = Color(0xFFFCFCFC),
                    style = TextStyle(fontSize = 28.sp, fontWeight= FontWeight.Bold)
                )
                Text(
                    text = currentArtist,
                    color = Color(0xFFFCFCFC),
                    style = TextStyle(fontSize = 24.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {/*viewModel.playPause()*/}) {
                        Icon(Icons.Filled.Repeat,
                            contentDescription = "Repeat",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(10.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        IconButton(onClick = {viewModel.prevSong()}) {
                            Icon(
                                Icons.Filled.SkipPrevious,
                                contentDescription = "Previous",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(onClick = {viewModel.playPause()}) {
                                if(!isPLaying)
                                Icon(Icons.Filled.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .size(50.dp))
                                else
                                    Icon(Icons.Filled.Pause,
                                        contentDescription = "Pause",
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .size(50.dp))
                            }
                        }

                        IconButton(
                            onClick = {viewModel.nextSong()},

                            ) {
                            Icon(
                                Icons.Filled.SkipNext,
                                contentDescription = "Next",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                    }
                    IconButton(onClick = {/*viewModel.playPause()*/}) {
                        Icon(Icons.Filled.Shuffle,
                            contentDescription = "Shuffle",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(10.dp)
                        )

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentSongPosition.playerFormat(),
                        color = Color(0xFFFCFCFC),
                        style = TextStyle(fontSize = 15.sp))
                    Slider(
                        value = currentSongPosition,
                        onValueChange = {},
                        valueRange = 0f..maxSizeSong,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.White,
                            inactiveTrackColor = Color.Gray
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    )
                    Text(
                        text = "0:30",
                        color = Color(0xFFFCFCFC),
                        style = TextStyle(fontSize = 15.sp)
                    )

                }

                Spacer(modifier = Modifier.height(25.dp))

            }
        }
    }
}

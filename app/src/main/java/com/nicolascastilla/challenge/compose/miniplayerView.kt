package com.nicolascastilla.challenge.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascastilla.challenge.ui.theme.CustomBlue
import com.nicolascastilla.challenge.viewmodels.MainViewModel

@Composable
fun MiniPlayerView(viewModel: MainViewModel) {
    val isPLaying by viewModel.isPlaying.collectAsState(initial = true)
    val currentTitle by viewModel.currentTitle.collectAsState(initial = "")
    val currentArtist by viewModel.currentArtist.collectAsState(initial = "")
    var isVisible by remember { mutableStateOf(false)}
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.setViewVisibility(true) }
            .background(CustomBlue),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = currentTitle,
            color = Color.White,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = currentArtist,
            color = Color.White,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                IconButton(onClick = {viewModel.prevSong()}) {
                    Icon(
                        Icons.Filled.SkipPrevious,
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {viewModel.playPause()}) {
                        if(!isPLaying)
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(20.dp))
                        else
                            Icon(
                                Icons.Filled.Pause,
                                contentDescription = "Pause",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(20.dp))
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
                            .size(20.dp)
                    )
                }
            }
        }

    }
}
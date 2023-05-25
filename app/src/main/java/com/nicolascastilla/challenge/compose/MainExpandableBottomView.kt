package com.nicolascastilla.challenge.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.nicolascastilla.challenge.ui.theme.CustomBlack
import com.nicolascastilla.challenge.viewmodels.MainViewModel

@Composable
fun MainExpandableBottomView(viewModel: MainViewModel) {
    val isTopViewVisible by viewModel.isTopViewVisible.collectAsState()
    val player by viewModel.player.observeAsState()
    AnimatedVisibility(
        visible = isTopViewVisible,
        enter = slideInVertically(initialOffsetY = { it }),  // Entrada animada desde la parte inferior
        exit = slideOutVertically(targetOffsetY = { it })  // Salida animada desde la parte superior
    ) {
        // Aquí va el contenido de la vista superior
        Box(
            modifier = Modifier.fillMaxSize()
                .background(CustomBlack)
        ) {
            Button(
                onClick = { viewModel.setViewVisibility(false) }, // al hacer clic en el botón, ocultamos la vista superior
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
            ) {
                Text(text = "Cerrar")
            }
            // Aquí puedes agregar más contenido a la vista superior
            Column {
                Text("Music Player")

                Row {
                    Button(onClick = { viewModel.playPause() }) {
                        Text("Play/Pause")
                    }

                    Button(onClick = { viewModel.stop() }) {
                        Text("Stop")
                    }

                    // Agrega aquí los botones de adelante y atrás
                }
            }
        }
    }
}
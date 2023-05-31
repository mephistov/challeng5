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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicolascastilla.challenge.compose.utils.RemoteImageFull
import com.nicolascastilla.challenge.ui.theme.*
import com.nicolascastilla.challenge.viewmodels.MainViewModel


@Composable
fun MainView() {
    val modifier: Modifier = Modifier
    val scaffolState = rememberScaffoldState()
    val viewModel = viewModel<MainViewModel>()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val isPLaying by viewModel.isPlaying.collectAsState(initial = false)

    viewModel.getGenereSearch("Pop")

    Scaffold(
        scaffoldState = scaffolState,
        backgroundColor = CustomBlack,
        drawerContent = {
            Column {
                Divider(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        navController.navigate("routeFavorites")
                        scope.launch { scaffolState.drawerState.close()}
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("FAVOURITES")
                }
                Divider(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        navController.navigate("routeFavorites")
                        scope.launch { scaffolState.drawerState.close()}
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("DUMMY BUTTON2")
                }
                // Agrega más elementos aquí
            }
        },
        drawerGesturesEnabled = true,
        drawerContentColor = MaterialTheme.colors.onSurface,
        drawerBackgroundColor = CustomGrey,
        drawerScrimColor = Color.Transparent,

    ) {
        modifier.padding(it)
        Box(
            modifier = Modifier
                .background(CustomBlack)
                .fillMaxWidth()
        ) {

            MyNavHost(navController,scaffolState,viewModel)

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
fun MyNavHost(
    navController: NavHostController,
    scaffolState: ScaffoldState,
    viewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = "routeMain") {
        composable("routeMain") { Screen1MainView(navController,scaffolState,viewModel) }
        composable("routeFavorites") { ScreenFavorites(navController,viewModel) }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}

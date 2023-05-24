package com.nicolascastilla.challenge.compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicolascastilla.challenge.compose.MainView
import com.nicolascastilla.challenge.ui.theme.ElemetBackgound

@Composable
fun ChallengeButton(icon : ImageVector,function: () -> Unit){
    //Icons.Default.Menu
    Button(
            onClick = { function() },
    shape = RoundedCornerShape(10),
    colors = ButtonDefaults.buttonColors(
        containerColor =  ElemetBackgound
    ),
    modifier = Modifier.size(50.dp),
    contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menu",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewB() {
    ChallengeButton(Icons.Default.Menu, {})
}
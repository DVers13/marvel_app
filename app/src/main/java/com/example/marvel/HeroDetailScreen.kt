package com.example.marvel

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HeroDetailScreen(hero: Hero, navigateBack: () -> Unit) {
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(hero.url)
                .crossfade(true)
                .build(),
            contentDescription = "background_image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(onClick = { navigateBack() },
            modifier = Modifier.padding(start = 10.dp, top = 24.dp)) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        Column(
            modifier = Modifier
                .padding(start = 30.dp, bottom = 20.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = hero.name,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = hero.description,
                style = TextStyle(fontSize = 16.sp),
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }
}
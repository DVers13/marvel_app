package com.example.marvel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.marvel.network.Result
import com.example.marvel.ui.utils.convertUrl


@Composable
fun HeroDetailScreen(hero: Result, navigateBack: () -> Unit) {
    Box {
        Log.d("dddd", "${hero.id}")
        AsyncImage(
            model = convertUrl(
                url = hero?.thumbnail?.path ?: "",
                extension = hero?.thumbnail?.extension ?: ""
            ),
            contentDescription = "background_image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = { navigateBack() },
            modifier = Modifier.padding(start = 10.dp, top = 24.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
        }
        Box(
            modifier = Modifier
                .padding(start = 30.dp, bottom = 20.dp, end = 30.dp)
                .align(Alignment.BottomStart)
                .background(color = Color.White.copy(alpha = 0.7f), shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = hero.name,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = hero.description,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

    }
}
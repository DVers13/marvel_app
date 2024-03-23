package com.example.marvel.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.marvel.network.Result
import com.example.marvel.ui.utils.convertUrl

@Composable
fun HeroCard(hero: Result, onClick: () -> Unit, imageSize: Float, modifier: Any) {
    val heroName = hero.name;
    Box(modifier = Modifier
        .padding(start = 30.dp, end = 30.dp, bottom = 20.dp)
        .fillMaxWidth()
        .graphicsLayer {
            scaleX = imageSize
            scaleY = imageSize
        }
        .clickable(onClick = onClick))
    {
        AsyncImage(
            model = convertUrl(
                url = hero?.thumbnail?.path ?: "",
                extension = hero?.thumbnail?.extension ?: ""
            ),
            contentDescription = "Card image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable(onClick = onClick)
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomStart)
        )
        {
            Text(
                text = heroName,
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(1f, 1f),
                        blurRadius = 3f
                    )
                )
            )
        }
    }
}


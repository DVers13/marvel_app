//package com.example.marvel
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//
//@Composable
//fun HeroCard(hero: Hero, onClick: () -> Unit, imageSize: Float) {
//    val heroName = hero.name;
//    val heroUrl = hero.url;
//    Box(modifier = Modifier.fillMaxSize().graphicsLayer { scaleX = imageSize
//        scaleY = imageSize})
//    {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(heroUrl)
//                .crossfade(true)
//                .build(),
//            contentDescription = null,
//            modifier = Modifier.clip(shape = RoundedCornerShape(8.dp))
//                .clickable(onClick = onClick)
//        )
//        Box(modifier =  Modifier.fillMaxSize().padding (start = 30.dp, bottom = 20.dp), contentAlignment = Alignment.BottomStart)
//        {
//            Text(heroName, textAlign = TextAlign.Center, color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
//        }
//    }
//}

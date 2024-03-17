@file:OptIn(ExperimentalFoundationApi::class)

package com.example.marvel


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlin.math.max


@Composable
fun HeroesPageScreen(navController: NavController) {
    val heroes = HeroList.heroList
    val lazyListState = rememberLazyListState()
    val snappingLayout = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
    val flingBehavior = rememberSnapFlingBehavior(snappingLayout)
//    val gradColorIndex =
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF2B272B),
            Color(0xFF2B272B),
            Color(0xFF2B272B),
            Color(0xFF991518), //heroes[gradColorIndex].color?
            Color(0xFF941419)  //heroes[gradColorIndex].color
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, bottom = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(50.dp),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                AsyncImage(
                    model = "https://iili.io/JMnuvbp.png",
                    contentDescription = "logo",
                    modifier = Modifier
                        .height(30.dp)
                        .width(130.dp)
                )
                Text(
                    modifier = Modifier
                        .height(30.dp)
                        .width(240.dp),
                    text = "Choose your hero",
                    textAlign = TextAlign.Center,
                    color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold
                )
            }
            BoxWithConstraints {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(38.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(550.dp)
                        .fillMaxWidth(),
                    state = lazyListState,
                    flingBehavior = flingBehavior
                ) {
                    items(heroes.size) { index ->
                        Layout(
                            content = {
                                val hero = heroes[index]
                                HeroCard(hero = hero) {
                                    navController.navigate(Screen.HeroDetail.passIndex(index))
                                }
                            },
                            measurePolicy = { measures, constraints ->
                                val placeable = measures.first().measure(constraints)

                                val maxWidthInPx = maxWidth.roundToPx()

                                val itemWidth = placeable.width

                                val startSpace =
                                    if (index == 0) (maxWidthInPx - itemWidth) / 2 else 0
                                val endSpace =
                                    if (index == heroes.size - 1) (maxWidthInPx - itemWidth) / 2 else 0

                                val width = startSpace + placeable.width + endSpace
                                layout(width, placeable.height) {

                                    val x = if (index == 0) startSpace else 0
                                    placeable.place(x, 0)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

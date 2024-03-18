@file:OptIn(ExperimentalFoundationApi::class)

package com.example.marvel


import android.annotation.SuppressLint
import android.service.autofill.Validators.or
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.marvel.HeroList.heroList
import com.example.marvel.ui.theme.BackgroundGray
import kotlin.math.abs


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeroesPageScreen(navController: NavController) {
    val heroes = remember { HeroList.heroList }
    val lazyListState = rememberLazyListState()
    val snappingLayout = remember(lazyListState) { SnapLayoutInfoProvider(lazyListState) }
    val flingBehavior = rememberSnapFlingBehavior(snappingLayout)

    val heroColorState = remember { mutableStateOf(Color(android.graphics.Color.parseColor(heroes[0].color))) }

    val gradient = Brush.linearGradient(
        colors = listOf(
            BackgroundGray,
            BackgroundGray,
            BackgroundGray,
            heroColorState.value,
            heroColorState.value
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
            LogoAndTitle()
            HeroesList(
                navController = navController,
                heroes = heroes,
                lazyListState = lazyListState,
                flingBehavior = flingBehavior,
                heroColorState = heroColorState
            )
        }
    }
}

@Composable
fun LogoAndTitle() {
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
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HeroesList(
    navController: NavController,
    heroes: List<Hero>,
    lazyListState: LazyListState,
    flingBehavior: SnapFlingBehavior,
    heroColorState: MutableState<Color>
) {
    BoxWithConstraints {
        val maxWidthInPx = maxWidth
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(550.dp)
                .fillMaxWidth(),
            state = lazyListState,
            flingBehavior = flingBehavior
        ) {
            items(heroes) { hero ->
                val layoutInfo = lazyListState.layoutInfo
                val visibleItems = layoutInfo.visibleItemsInfo
                val centerX = constraints.maxWidth / 2

                val closestItem = visibleItems.minByOrNull {
                    val itemCenterX = it.offset + it.size / 2
                    abs(centerX - itemCenterX)
                }

                val delta = 0.1f
                val density = LocalDensity.current
                var pageOffset = closestItem?.let {
                    val itemCenterX = it.offset + it.size / 2
                    val distanceFromCenter = abs(centerX - itemCenterX)
                    val maxWidthInPxFloat = with(density) { maxWidthInPx.toPx() }
                    distanceFromCenter / maxWidthInPxFloat
                } ?: 0f

                if (pageOffset <= delta && pageOffset >= -delta){
                    pageOffset = 0f
            }
                Log.d("DDD", "$pageOffset")

                val imageSize by animateFloatAsState(
                    targetValue = if(pageOffset != 0.0f) 0.75f else 1f,
                    animationSpec = tween(durationMillis = 300)
                )

                val centerHeroId = closestItem?.index ?: -1
                val centerHeroColor = if (centerHeroId != -1) {
                    val color = heroes.getOrNull(centerHeroId)?.color
                    Color(android.graphics.Color.parseColor(color))
                } else {
                    Color.Transparent
                }

                heroColorState.value = centerHeroColor

                HeroItem(
                    hero = hero,
                    onItemClick = { navController.navigate(Screen.HeroDetail.passIndex(hero.id)) },
                    maxWidthInPx,
                    imageSize
                )
            }
        }
    }
}

@Composable
fun HeroItem(hero: Hero, onItemClick: () -> Unit, maxWidth: Dp, imageSize: Float) {
    Layout(
        content = {
            HeroCard(hero = hero, onClick = onItemClick, imageSize)
        },
        measurePolicy = { measurables, constraints ->
            val placeable = measurables.first().measure(constraints)

            val maxWidthInPx = maxWidth.toPx()

            val itemWidth = placeable.width

            val startSpace =
                if (hero.id == 0) (maxWidthInPx - itemWidth) / 2 else 0
            val endSpace =
                if (hero.id == heroList.size - 1) (maxWidthInPx - itemWidth) / 2 else 0

            val width = (startSpace.toFloat() + placeable.measuredWidth.toFloat() + endSpace.toFloat())

            layout(width.toInt(), placeable.height) {

                val x = if (hero.id == 0) startSpace.toInt() else 0
                placeable.place(x, 0)
            }
        }
    )
}


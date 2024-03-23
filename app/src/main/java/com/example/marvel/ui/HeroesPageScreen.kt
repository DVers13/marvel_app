@file:OptIn(ExperimentalFoundationApi::class)

package com.example.marvel.ui


import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.marvel.ui.components.HeroCard
import com.example.marvel.R
import com.example.marvel.network.Result
import com.example.marvel.ui.theme.BackgroundGray
import com.example.marvel.ui.utils.colors


@Composable
fun HeroesPageScreen(navController: NavController, heroes: List<Result>) {

    val pagerState = rememberPagerState(pageCount = { heroes.size })
    val gradient = Brush.linearGradient(
        colors = listOf(
            BackgroundGray,
            BackgroundGray,
            BackgroundGray,
            colors[pagerState.currentPage],
            colors[pagerState.currentPage]
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
                pagerState = pagerState
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
            text = stringResource(R.string.choose_hero),
            textAlign = TextAlign.Center,
            color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HeroesList(
    navController: NavController,
    heroes: List<Result>,
    pagerState: PagerState
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.background(Color.Transparent).fillMaxSize()
        ) { index ->
            val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300)
            )
            HeroCard(
                hero = heroes[index],
                onClick = { navController.navigate(Screen.HeroDetail.passIndex(index)) },
                imageSize,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

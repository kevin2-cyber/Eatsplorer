package com.kimikevin.eatsplorer.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kimikevin.eatsplorer.R
import com.kimikevin.eatsplorer.model.entity.Onboarding
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit
) {
    val items = listOf(
        Onboarding().apply {
            setTitle("Satisfy your cravings \nwith ease")
            setDescription("Integer a viverra sit feugiat leo\nncommodo nunc.")
            setImage(R.drawable.onboarding_image_1)
        },
        Onboarding().apply {
            setTitle("Find your new favourite \nrestaurant with just a tap")
            setDescription("Integer a viverra sit feugiat leo\nncommodo nunc.")
            setImage(R.drawable.onboarding_image_2)
        },
        Onboarding().apply {
            setTitle("Fresh meals, delivered to your doorstep")
            setDescription("Integer a viverra sit feugiat leo\nncommodo nunc.")
            setImage(R.drawable.onboarding_image_3)
        }
    )

    val pagerState = rememberPagerState(pageCount = { items.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingItemView(items[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicators
            Row {
                repeat(items.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)
                    )
                }
            }

            Row {
                if (pagerState.currentPage < items.size - 1) {
                    TextButton(onClick = {
                        scope.launch { pagerState.scrollToPage(items.size - 1) }
                    }) {
                        Text(stringResource(R.string.skip))
                    }
                }

                Button(onClick = {
                    if (pagerState.currentPage < items.size - 1) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        onFinished()
                    }
                }) {
                    Text(if (pagerState.currentPage == items.size - 1) stringResource(R.string.get_started) else stringResource(R.string.next))
                }
            }
        }
    }
}

@Composable
fun OnboardingItemView(item: Onboarding) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = item.getImage()),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = item.getTitle(),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = item.getDescription(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

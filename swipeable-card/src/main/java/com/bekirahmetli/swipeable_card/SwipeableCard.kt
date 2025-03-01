package com.bekirahmetli.swipeable_card

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import kotlin.math.absoluteValue
import coil.compose.AsyncImage

@Composable
fun SwipeableCard(
    cardData: CardData?,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 350.dp,
    cardHeight: Dp = 250.dp,
    imageHeight: Dp = 150.dp,
    cornerRadius: Int = 16,
    sensitivity: Float = 200f,
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {}
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isLoading by remember { mutableStateOf(true) }

    val animatedOffsetX = animateFloatAsState(targetValue = offsetX)
    val rotationAngle = (offsetX / 10).coerceIn(-15f, 15f)
    val alphaValue = (1 - (offsetX / 600f).absoluteValue).coerceIn(0f, 1f)
    val scale = remember { Animatable(1f) }

    LaunchedEffect(cardData) {
        isLoading = cardData == null
    }

    Box(
        modifier = modifier
            .width(cardWidth)
            .height(cardHeight)
            .offset(x = animatedOffsetX.value.dp)
            .graphicsLayer(rotationZ = rotationAngle, alpha = alphaValue)
            .scale(scale.value)
            .background(Color.LightGray, RoundedCornerShape(cornerRadius.dp))
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        when {
                            offsetX > sensitivity -> {
                                onSwipeRight()
                                offsetX = 1000f
                            }
                            offsetX < -sensitivity -> {
                                onSwipeLeft()
                                offsetX = -1000f
                            }
                            else -> offsetX = 0f
                        }
                    }
                ) { _, dragAmount -> offsetX += dragAmount }
            }
    ) {
        if (isLoading) {
            EffectLoader(modifier = Modifier.fillMaxSize())
        } else if (cardData != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = cardData.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = cardData.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6200EE),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = cardData.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Button(
                    onClick = { println("Button clicked") },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Detail")
                }
            }
        }
    }
}


@Composable
fun CardStack(
    cards: List<CardData>,
    modifier: Modifier = Modifier,
    cardModifier: Modifier = Modifier,
    onSwipeLeft: (CardData) -> Unit = {},
    onSwipeRight: (CardData) -> Unit = {}
) {
    var currentIndex by remember { mutableStateOf(0) }

    if (currentIndex < cards.size) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            for (index in (currentIndex until cards.size).reversed()) {
                val scale = 1f - (index - currentIndex) * 0.05f
                val yOffset = (index - currentIndex) * 16.dp

                SwipeableCard(
                    cardData = cards[index],
                    modifier = cardModifier
                        .scale(scale)
                        .offset(y = yOffset),
                    onSwipeLeft = {
                        onSwipeLeft(cards[index])
                        currentIndex++
                    },
                    onSwipeRight = {
                        onSwipeRight(cards[index])
                        currentIndex++
                    }
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "You swiped all the cards!",
                fontSize = 24.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
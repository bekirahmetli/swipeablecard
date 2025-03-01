package com.bekirahmetli.swipeable_card

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun EffectLoader(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()

    val translateAnim = infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
                .graphicsLayer(translationX = translateAnim.value * 200f)
        )

        Spacer(modifier = Modifier.height(12.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .background(Color.Gray)
                .graphicsLayer(translationX = translateAnim.value * 200f)
        )

        Spacer(modifier = Modifier.height(8.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .background(Color.Gray)
                .graphicsLayer(translationX = translateAnim.value * 200f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(14.dp)
                .background(Color.Gray)
                .graphicsLayer(translationX = translateAnim.value * 200f)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Box(
            modifier = Modifier
                .width(80.dp)
                .height(36.dp)
                .background(Color.DarkGray, RoundedCornerShape(8.dp))
                .graphicsLayer(translationX = translateAnim.value * 200f)
        )
    }
}
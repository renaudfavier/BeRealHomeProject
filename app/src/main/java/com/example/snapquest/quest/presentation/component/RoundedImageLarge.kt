package com.example.snapquest.quest.presentation.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoundedImageLarge(
    imageUrl: String?,
    modifier: Modifier = Modifier
) = Surface(
    modifier = modifier
        .aspectRatio(16 / 11f),
    shape = RoundedCornerShape(24.dp),
) {
    ImageWithPlaceholder(
        imageUrl = imageUrl
    )
}
package com.example.snapquest.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.snapquest.ui.theme.SnapQuestTheme

@Composable
fun ErrorImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "warning icon",
                tint = Color.Red
            )
            Text("failed to load image", modifier = Modifier.padding(start = 6.dp))
        }
    }
}

@Preview
@Composable
private fun ErrorImagePreview() {
    SnapQuestTheme {
        ErrorImage()
    }
}

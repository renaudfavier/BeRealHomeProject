package com.example.snapquest.quest.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionTitle(
    text: String, modifier:
    Modifier = Modifier
) = Text(
    text = text,
    modifier = modifier
        .padding(bottom = 18.dp, top = 36.dp),
    color = MaterialTheme.colorScheme.secondary,
    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
)
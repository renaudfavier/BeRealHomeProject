package com.example.snapquest.quest.domain.model

import java.time.Instant

data class Quest(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val endDate: Instant,
    val description: String,
)

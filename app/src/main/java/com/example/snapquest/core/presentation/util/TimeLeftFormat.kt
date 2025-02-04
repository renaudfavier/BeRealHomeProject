package com.example.snapquest.core.presentation.util

import java.time.Duration
import java.time.Instant

fun formatTimeLeft(instant: Instant): String? {
    if(instant.isBefore(Instant.now())) return null

    val duration = Duration.between(Instant.now(), instant)
    return when {
        duration.toHours() > 0 -> "${duration.toHours()} hours left"
        duration.toMinutes() > 0 -> "${duration.toMinutes()} minutes left"
        else -> "Few seconds left"
    }
}
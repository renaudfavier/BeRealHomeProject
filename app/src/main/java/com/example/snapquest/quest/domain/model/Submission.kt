package com.example.snapquest.quest.domain.model

data class Submission(
    val id: Int,
    val questId: Int,
    val url: String,
    val voteCount: Int,
    val isMine: Boolean,
)

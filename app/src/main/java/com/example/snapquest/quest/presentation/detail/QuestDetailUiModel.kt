package com.example.snapquest.quest.presentation.detail

sealed interface QuestDetailUiModel {

    data object Loading : QuestDetailUiModel

    data class Content(
        val title: String,
        val description: String,
        val questImageUrl: String?,
        val timeLeft: String,
        val yourSubmission: Submission?,
        val mostUpVoted: Submission?,
        val allSubmission: List<Submission>,

    ) : QuestDetailUiModel

    data class Error(val message: String) : QuestDetailUiModel
}

data class Submission(
    val id: Int,
    val url: String,
    val voteCount: Int,
)

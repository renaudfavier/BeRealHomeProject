package com.example.snapquest.quest.presentation.detail

sealed interface QuestDetailUiModel {

    data object Loading : QuestDetailUiModel

    data class Content(
        val name: String,
    ) : QuestDetailUiModel

    data class Error(val message: String) : QuestDetailUiModel
}
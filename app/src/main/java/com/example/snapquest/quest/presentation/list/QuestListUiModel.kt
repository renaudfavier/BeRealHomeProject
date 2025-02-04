package com.example.snapquest.quest.presentation.list

sealed interface QuestListUiModel {

    data object Loading : QuestListUiModel

    data class Content(
        val categories: List<CategoryUiModel>,
    ) : QuestListUiModel

    data class Error(val message: String) : QuestListUiModel
}

data class CategoryUiModel(val title: String, val quests: List<QuestUiModel>)

data class QuestUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val timeLeft: String?,
    val isDesaturated: Boolean,
)

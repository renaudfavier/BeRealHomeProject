package com.example.snapquest.quest.presentation.list

sealed interface QuestListUiModel {

    data object Loading : QuestListUiModel

    data class Content(
        val dailyQuest: QuestUiModel,
    ) : QuestListUiModel

    data class Error(val message: String) : QuestListUiModel
}

data class QuestUiModel(val name: String)

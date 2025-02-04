package com.example.snapquest.quest.domain

import com.example.snapquest.core.domain.Error
import com.example.snapquest.core.domain.Result

interface AvailableQuestsRepository {

    enum class AvailableQuestsRepositoryError: Error

    data class Response(
        val categories: List<QuestCategory>
    )

    data class QuestCategory(
        val categoryName: String,
        val questsIds: List<Int>,
    )

    suspend fun getQuests(): Result<Response, AvailableQuestsRepositoryError>

}


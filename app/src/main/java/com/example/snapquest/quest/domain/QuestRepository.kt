package com.example.snapquest.quest.domain

import com.example.snapquest.core.domain.Error
import com.example.snapquest.core.domain.Result
import com.example.snapquest.quest.domain.model.Quest

interface QuestRepository {

    enum class QuestRepositoryError: Error {
        QUEST_NOT_FOUND
    }

    suspend fun getQuest(id: Int): Result<Quest, QuestRepositoryError>
}

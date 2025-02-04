package com.example.snapquest.quest.data

import com.example.snapquest.core.domain.Result
import com.example.snapquest.quest.domain.AvailableQuestsRepository
import com.example.snapquest.quest.domain.AvailableQuestsRepository.AvailableQuestsRepositoryError
import com.example.snapquest.quest.domain.AvailableQuestsRepository.QuestCategory
import com.example.snapquest.quest.domain.AvailableQuestsRepository.Response
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeAvailableQuestsRepository @Inject constructor(): AvailableQuestsRepository {

    private val availableQuests :List<QuestCategory> = listOf(
        QuestCategory("Today's Quest", listOf(1)),
        QuestCategory("Past Quest you Joined", listOf(2, 3)),
    )

    override suspend fun getQuests(): Result<Response, AvailableQuestsRepositoryError> {
        //Simulate network call
        delay(400L)
        return Result.Success(Response(availableQuests))
    }
}

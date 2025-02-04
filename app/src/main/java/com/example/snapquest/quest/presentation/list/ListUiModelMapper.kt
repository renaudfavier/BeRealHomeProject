package com.example.snapquest.quest.presentation.list

import com.example.snapquest.core.presentation.util.formatTimeLeft
import com.example.snapquest.quest.domain.AvailableQuestsRepository
import com.example.snapquest.quest.domain.model.Quest
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

class ListUiModelMapper @Inject constructor() {

    fun map(available: AvailableQuestsRepository.Response, questMap: Map<Int, Quest>)
    = QuestListUiModel.Content(
        categories = available.categories.map { category ->
            CategoryUiModel(
                title = category.categoryName,
                quests = category
                    .questsIds
                    .mapNotNull { questMap[it] }
                    .map { it.toUiModel() }
            )
        }
    )

    private fun Quest.toUiModel() = QuestUiModel(
        id = id,
        name = title,
        imageUrl = imageUrl,
        timeLeft = formatTimeLeft(endDate),
        isDesaturated = endDate.isBefore(Instant.now())
    )


}
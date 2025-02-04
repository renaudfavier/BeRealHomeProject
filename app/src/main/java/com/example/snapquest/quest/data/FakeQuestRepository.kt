package com.example.snapquest.quest.data

import com.example.snapquest.core.domain.Result
import com.example.snapquest.quest.domain.QuestRepository
import com.example.snapquest.quest.domain.model.Quest
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class FakeQuestRepository @Inject constructor(): QuestRepository {

    private val somethingBlue = Quest(
        id = 1,
        title = "Something Blue",
        imageUrl = "https://i.imgur.com/43v592j.jpeg",
        endDate = LocalDate.now().atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC),
        description = "Discover the world in blue! Submit your most captivating azure-hued photographs—from ocean depths to sky whispers, urban blues to natural wonders. Creativity meets color in our mesmerizing \"Something Blue\" contest."
    )

    private val coffeeArt = Quest(
        id = 2,
        title = "Coffee Art",
        imageUrl = "https://www.ueshimacoffeecompany.com/cdn/shop/articles/AdobeStock_187364349.jpg",
        endDate = LocalDate.now().atStartOfDay().minusDays(3).toInstant(ZoneOffset.UTC),
        description = "Unleash creativity in this photo contest celebrating coffee’s beauty. Capture latte art, cozy moments, or brewing rituals for a chance to showcase your talent"
    )

    private val forest = Quest(
        id = 3,
        title = "Forest",
        imageUrl = "https://onetreeplanted.org/cdn/shop/articles/Forest_Fog_1600x.jpg?v=1682535224",
        endDate = LocalDate.now().atStartOfDay().minusDays(3).toInstant(ZoneOffset.UTC),
        description = "A photo contest inviting you to explore nature’s wonders. Capture the essence of forests—towering trees, vibrant wildlife, and peaceful landscapes—and share your unique perspective."
    )


    private val questMap = mutableMapOf(
        1 to somethingBlue,
        2 to coffeeArt,
        3 to forest,
    )

    override suspend fun getQuest(id: Int): Result<Quest, QuestRepository.QuestRepositoryError> {
        //Simulate network call
        delay(500L)

        return questMap[id]?.let { Result.Success(it) }
            ?: Result.Error(QuestRepository.QuestRepositoryError.QUEST_NOT_FOUND)

    }

    override suspend fun getQuests(ids: Set<Int>): Result<Map<Int, Quest>, QuestRepository.QuestRepositoryError> {
        //Simulate network call
        delay(500L)

        val found = questMap.filter { it.key in ids }

        return if (found.size == ids.size) Result.Success(found)
        else Result.Error(QuestRepository.QuestRepositoryError.QUEST_NOT_FOUND)
    }
}

package com.example.snapquest.quest.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.snapquest.core.domain.Result
import com.example.snapquest.quest.domain.QuestRepository
import com.example.snapquest.quest.domain.model.Quest
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class FakeQuestRepository @Inject constructor(): QuestRepository {

    private val somethingBlue = Quest(
        id = 1,
        title = "Something Blue",
        imageUrl = "https://i.imgur.com/43v592j.jpeg",
        endDate = LocalDate.now().atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC),
        description = "Discover the world in blue! Submit your most captivating azure-hued photographs—from ocean depths to sky whispers, urban blues to natural wonders. Creativity meets color in our mesmerizing \"Something Blue\" contest."
    )

    override suspend fun getQuest(id: Int): Result<Quest, QuestRepository.QuestRepositoryError> {
        //Simulate network call
        delay(500L)

        val quest = when(id) {
            1 -> somethingBlue
            else -> null
        }
        return if(quest != null)
            Result.Success(quest)
            else Result.Error(QuestRepository.QuestRepositoryError.QUEST_NOT_FOUND)
    }
}

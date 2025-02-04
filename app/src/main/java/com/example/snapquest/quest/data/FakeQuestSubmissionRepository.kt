package com.example.snapquest.quest.data

import com.example.snapquest.core.domain.Error
import com.example.snapquest.core.domain.Result
import com.example.snapquest.quest.domain.QuestSubmissionRepository
import com.example.snapquest.quest.domain.model.Submission
import javax.inject.Inject

class FakeQuestSubmissionRepository @Inject constructor(): QuestSubmissionRepository {

    private val somethingBlueSubmissions: List<Submission> = listOf(
        Submission(11, 1, "https://i.imgur.com/f7wtzCS.png", 134, false),
        Submission(12, 1, "https://i.imgur.com/kq2hntt.jpeg", 24, false),
        Submission(13, 1, "https://i.imgur.com/oOnfqSR.jpeg", 82, false),
    )
    private val submissions: MutableMap<Int, MutableList<Submission>> = mutableMapOf(
        1 to somethingBlueSubmissions.toMutableList()
    )

    override suspend fun getAllSubmissions(questId: Int): Result<QuestSubmissionRepository.Response, Error> {
        return Result.Success((submissions[questId] ?: emptyList<Submission>()).toResponse())
    }

    private fun List<Submission>.toResponse(): QuestSubmissionRepository.Response {
        val yours = this.firstOrNull { it.isMine }
        val mostVoted = this.maxBy { it.voteCount }
        return QuestSubmissionRepository.Response(
            mostVoted = mostVoted,
            yours = yours,
            allSubmission = this
        )
    }

    override suspend fun submit(
        questId: Int,
        uploadedPhoto: Pair<Int, String>
    ): Result<Unit, Error> {
        if(questId !in submissions) submissions[questId] = mutableListOf()
        submissions[questId]?.add(
            Submission(uploadedPhoto.first, questId, uploadedPhoto.second, 0, true)
        )
        return Result.Success(Unit)
    }
}

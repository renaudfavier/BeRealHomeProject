package com.example.snapquest.quest.domain

import com.example.snapquest.core.domain.Error
import com.example.snapquest.core.domain.Result
import com.example.snapquest.quest.domain.model.Submission

interface QuestSubmissionRepository {

    data class Response(
        val mostVoted: Submission?,
        val yours: Submission?,
        val allSubmission: List<Submission>
    )

    suspend fun getAllSubmissions(questId: Int): Result<Response, Error>
    suspend fun submit(questId: Int, uploadedPhoto: Pair<Int, String>): Result<Unit, Error>
}

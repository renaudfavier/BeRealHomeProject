package com.example.snapquest.quest.presentation.detail

import com.example.snapquest.quest.domain.model.Quest
import com.example.snapquest.quest.domain.QuestSubmissionRepository
import com.example.snapquest.quest.domain.model.Submission
import java.time.Instant
import javax.inject.Inject

class QuestUiModelMapper @Inject constructor() {

    fun map(quest: Quest, submissions: QuestSubmissionRepository.Response)
    = QuestDetailUiModel.Content(
        title = quest.title,
        description = quest.description,
        questImageUrl = quest.imageUrl,
        timeLeft = formatTimeLeft(quest.endDate),
        yourSubmission = submissions.yours?.toUiModel(),
        mostUpVoted = submissions.mostVoted?.toUiModel(),
        allSubmission = submissions.allSubmission.map { it.toUiModel() },
        isJoinButtonVisible = submissions.yours == null
    )

    private fun formatTimeLeft(instant: Instant): String {
        return instant.toString()
    }

    private fun Submission.toUiModel() = SubmissionUiModel(
        id = id,
        url = url,
        voteCount = voteCount
    )

}
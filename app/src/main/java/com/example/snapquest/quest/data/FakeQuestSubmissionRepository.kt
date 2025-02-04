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
    private val coffeeArtSubmissions: List<Submission> = listOf(
        Submission(21, 2, "https://upload.wikimedia.org/wikipedia/commons/7/7c/Cappuccino_Chiang_Mai.JPG", 18, false),
        Submission(22, 2, "https://media.timeout.com/images/105526401/image.jpg", 75, false),
        Submission(23, 2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsFGH7P_qQIvSrxsaWTP3b1fZVlyEp5cqMIA&s", 121, false),
        Submission(24, 2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVeunkhmW2i1XpN4Al2i0wJg2g6EXFH1EyUw&s", 7, false),
        Submission(25, 2, "https://i.pinimg.com/736x/56/57/be/5657be703a4f1b274ae7444b46690d86.jpg", 182, true),
    )

    private val forestSubmissions: List<Submission> = listOf(
        Submission(31, 3, "https://www.massaudubon.org/var/site/storage/images/9/3/6/2/1602639-1-eng-US/2fe6f73efaea-RE_KForesto-3461-1920x1280.jpg", 28, false),
        Submission(31, 3, "https://images.nationalgeographic.org/image/upload/v1638892272/EducationHub/photos/hoh-river-valley.jpg", 18, false),
        Submission(31, 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSePvI_cF2gdEOaNkE-ArSnmkvVrd76RXJYbg&s", 7, true),
        Submission(31, 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQSaCI3mCHMkmkvjG2tl0foZ4wR3EaEd2VkAg&s", 1, false),
    )
    private val submissions: MutableMap<Int, MutableList<Submission>> = mutableMapOf(
        1 to somethingBlueSubmissions.toMutableList(),
        2 to coffeeArtSubmissions.toMutableList(),
        3 to forestSubmissions.toMutableList(),
    )

    override suspend fun getAllSubmissions(questId: Int): Result<QuestSubmissionRepository.Response, Error> {
        return Result.Success((submissions.getOrDefault(questId, null) ?: emptyList()).toResponse())
    }

    override suspend fun getSubmission(id: Int): Result<Submission, Error> {
        somethingBlueSubmissions.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        coffeeArtSubmissions.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        forestSubmissions.firstOrNull { it.id == id }?.let { return Result.Success(it) }
        return Result.Error(QuestSubmissionRepository.QuestSubmissionRepositoryError.NOT_FOUND)
    }

    private fun List<Submission>.toResponse(): QuestSubmissionRepository.Response {
        val yours = this.firstOrNull { it.isMine }
        val mostVoted = this.maxByOrNull { it.voteCount }
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

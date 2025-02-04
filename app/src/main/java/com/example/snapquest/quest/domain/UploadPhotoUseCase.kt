package com.example.snapquest.quest.domain

import android.net.Uri
import com.example.snapquest.quest.presentation.detail.Submission
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

interface UploadPhotoUseCase {
    suspend fun uploadPhoto(uri: Uri): Result<Submission>
}

class FakeUploadPhotoUseCase @Inject constructor(): UploadPhotoUseCase {
    override suspend fun uploadPhoto(uri: Uri): Result<Submission> {
        delay(1000L)
        return Result.success(
            Submission(
                Random.nextInt(),
                url = uri.toString(),
                voteCount = 0,
            )
        )
    }
}
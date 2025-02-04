package com.example.snapquest.quest.data

import android.net.Uri
import com.example.snapquest.quest.domain.UploadPhotoUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class FakeUploadPhotoUseCase @Inject constructor(): UploadPhotoUseCase {
    override suspend fun uploadPhoto(uri: Uri): Result<Pair<Int, String>> {
        delay(1000L)
        return Result.success(Pair(Random.nextInt(), uri.toString()))
    }
}

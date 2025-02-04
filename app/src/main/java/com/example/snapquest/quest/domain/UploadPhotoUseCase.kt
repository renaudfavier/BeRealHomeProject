package com.example.snapquest.quest.domain

import android.net.Uri

interface UploadPhotoUseCase {
    suspend fun uploadPhoto(uri: Uri): Result<Pair<Int, String>>
}

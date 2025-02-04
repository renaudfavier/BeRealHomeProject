package com.example.snapquest.quest.di

import com.example.snapquest.quest.domain.FakeUploadPhotoUseCase
import com.example.snapquest.quest.domain.UploadPhotoUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface QuestModule {

    @Binds
    fun bindUploadPhotoUseCase(fake: FakeUploadPhotoUseCase): UploadPhotoUseCase
}
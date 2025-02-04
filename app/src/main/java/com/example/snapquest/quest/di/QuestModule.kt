package com.example.snapquest.quest.di

import com.example.snapquest.quest.data.FakeUploadPhotoUseCase
import com.example.snapquest.quest.domain.UploadPhotoUseCase
import com.example.snapquest.quest.data.FakeQuestRepository
import com.example.snapquest.quest.data.FakeQuestSubmissionRepository
import com.example.snapquest.quest.domain.QuestRepository
import com.example.snapquest.quest.domain.QuestSubmissionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface QuestModule {

    @Singleton
    @Binds
    fun bindUploadPhotoUseCase(fake: FakeUploadPhotoUseCase): UploadPhotoUseCase

    @Singleton
    @Binds
    fun bindQuestRepository(fake: FakeQuestRepository): QuestRepository

    @Singleton
    @Binds
    fun bindQuestSubmissionRepository(fake: FakeQuestSubmissionRepository): QuestSubmissionRepository
}
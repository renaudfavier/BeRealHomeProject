package com.example.snapquest.quest.presentation.detail

import android.net.Uri
import com.example.avivhomeproject.rule.SavedStateHandleRule
import com.example.snapquest.QuestDetailRoute
import com.example.snapquest.quest.domain.FakeUploadPhotoUseCase
import com.example.snapquest.quest.domain.UploadPhotoUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val route = QuestDetailRoute(1)

    // https://issuetracker.google.com/issues/349807172?pli=1
    @get:Rule
    val savedStateHandleRule = SavedStateHandleRule(route)

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var uploadPhotoUseCase: UploadPhotoUseCase

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        uploadPhotoUseCase = FakeUploadPhotoUseCase()
        viewModel = DetailViewModel(
            savedStateHandle = savedStateHandleRule.savedStateHandleMock,
            uploadPhotoUseCase,
            Dispatchers.Main

        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel is created, state is Loading`() = runTest {

        // Create an empty collector for the StateFlow
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }


        assertEquals(QuestDetailUiModel.Loading, viewModel.uiState.value)
    }

    @Test
    fun `after loading, state is content`() = runTest {
        // Create an empty collector for the StateFlow
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }

        testScheduler.advanceUntilIdle()

        assert(viewModel.uiState.value is QuestDetailUiModel.Content)
    }

    @Test
    fun `when onPhotoSubmitted is called, calls use case`() = runTest {

        // Create an empty collector for the StateFlow
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect {}
        }

        //Given
        val uri = mockk<Uri>()

        //When
        testScheduler.advanceUntilIdle()
        viewModel.onPhotoSubmitted(uri)
        testScheduler.advanceUntilIdle()

        //Then
        coVerify { uploadPhotoUseCase.uploadPhoto(uri) }
    }
}

package com.example.snapquest.quest.presentation.detail

import com.example.avivhomeproject.rule.SavedStateHandleRule
import com.example.snapquest.QuestDetailRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
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

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(
            savedStateHandle = savedStateHandleRule.savedStateHandleMock,
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
}

package com.example.snapquest.quest.presentation.fullscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.snapquest.FullScreenRoute
import com.example.snapquest.core.di.IoDispatcher
import com.example.snapquest.core.domain.Result
import com.example.snapquest.quest.domain.QuestSubmissionRepository
import com.example.snapquest.quest.domain.model.Submission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.snapquest.quest.presentation.fullscreen.FullScreenUiModel as UiModel

@HiltViewModel
class FullScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questSubmissionRepository: QuestSubmissionRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val submissionId = savedStateHandle.toRoute<FullScreenRoute>().id

    private val _uiState = MutableStateFlow<UiModel?>(null)
    val uiState = _uiState
        .onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            null
        )

    private fun loadData() = viewModelScope.launch {
        _uiState.update { null }
        withContext(ioDispatcher) {
            when(val result = questSubmissionRepository.getSubmission(submissionId)) {
                is Result.Error -> {}
                is Result.Success -> {
                    withContext(Dispatchers.Main) {
                        _uiState.update { result.data.toUiModel() }
                    }
                }
            }
        }
    }

    private fun Submission.toUiModel() = UiModel(
        imageUrl = url
    )

}

package com.example.snapquest.quest.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.snapquest.QuestDetailRoute
import com.example.snapquest.quest.presentation.detail.QuestDetailUiModel as UiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val questId = savedStateHandle.toRoute<QuestDetailRoute>().id

    private val _uiState = MutableStateFlow<UiModel>(UiModel.Loading)
    val uiState = _uiState
        .onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiModel.Loading
        )

    private fun loadData() = viewModelScope.launch {
        _uiState.update { UiModel.Loading }
        withContext(Dispatchers.IO) {
            delay(1000L)
        }
        _uiState.update { UiModel.Content(
            "Something blue $questId",
            description = TODO(),
            questImageUrl = TODO(),
            timeLeft = TODO(),
            yourSubmission = TODO(),
            mostUpVoted = TODO(),
            allSubmission = TODO()
        ) }
    }
}

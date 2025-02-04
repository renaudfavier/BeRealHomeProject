package com.example.snapquest.quest.presentation.detail

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.snapquest.QuestDetailRoute
import com.example.snapquest.core.di.IoDispatcher
import com.example.snapquest.quest.domain.UploadPhotoUseCase
import com.example.snapquest.quest.presentation.detail.QuestDetailUiModel
import com.example.snapquest.quest.presentation.detail.QuestDetailUiModel as UiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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
        withContext(ioDispatcher) {
            delay(1000L)
        }
        _uiState.update { UiModel.Content(
            "Something blue $questId",
            description = "",
            questImageUrl = "TODO()",
            timeLeft = "TODO()",
            yourSubmission = null,
            mostUpVoted = null,
            allSubmission = emptyList()
        ) }
    }

    fun onPhotoSubmitted(uri: Uri?) = viewModelScope.launch {
        if(uri == null) return@launch

        // This is bad but too long to make better
        val storedState = _uiState.value as UiModel.Content

        _uiState.update { QuestDetailUiModel.Loading }
        withContext(ioDispatcher) {
            uploadPhotoUseCase.uploadPhoto(uri).fold(
                onSuccess = { submission ->
                    _uiState.update {
                        storedState.copy(yourSubmission = submission)
                    }
                },
                onFailure = { TODO() }
            )
        }
    }

}

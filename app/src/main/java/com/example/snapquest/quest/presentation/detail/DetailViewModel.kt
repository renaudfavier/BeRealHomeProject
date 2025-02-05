package com.example.snapquest.quest.presentation.detail

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.snapquest.QuestDetailRoute
import com.example.snapquest.core.di.IoDispatcher
import com.example.snapquest.core.domain.combine
import com.example.snapquest.core.domain.onError
import com.example.snapquest.core.domain.onSuccess
import com.example.snapquest.quest.domain.UploadPhotoUseCase
import com.example.snapquest.quest.domain.QuestRepository
import com.example.snapquest.quest.domain.QuestSubmissionRepository
import com.example.snapquest.quest.presentation.detail.QuestDetailUiModel
import com.example.snapquest.quest.presentation.detail.QuestDetailUiModel as UiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    private val questRepository: QuestRepository,
    private val questSubmissionRepository: QuestSubmissionRepository,
    private val mapper: QuestUiModelMapper,
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
        val deferredQuest = async(ioDispatcher) { questRepository.getQuest(questId) }
        val deferredSubmissions = async(ioDispatcher) { questSubmissionRepository.getAllSubmissions(questId) }

        deferredQuest.await()
            .combine(deferredSubmissions.await())
            .onSuccess { (quest, submissions) ->
                _uiState.update { mapper.map(quest, submissions) }
            }
            .onError { e ->
                _uiState.update { UiModel.Error(e.toString()) }
            }
    }

    fun onPhotoSubmitted(uri: Uri?) = viewModelScope.launch {
        if(uri == null) return@launch

        _uiState.update { QuestDetailUiModel.Loading }
        withContext(ioDispatcher) {
            uploadPhotoUseCase.uploadPhoto(uri).fold(
                onSuccess = { uploadedPhoto ->
                    questSubmissionRepository
                        .submit(questId, uploadedPhoto)
                        .onSuccess { loadData() }
                        .onError { displayError(it.toString()) }
                },
                onFailure = { displayError(it.toString()) }
            )
        }
    }

    private suspend fun displayError(message: String) = withContext(Dispatchers.Main) {
        _uiState.update {
            UiModel.Error(message)
        }
    }

}

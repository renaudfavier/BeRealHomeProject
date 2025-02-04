package com.example.snapquest.quest.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snapquest.core.domain.Result
import com.example.snapquest.quest.domain.AvailableQuestsRepository
import com.example.snapquest.quest.domain.QuestRepository
import com.example.snapquest.quest.presentation.list.QuestListUiModel as UiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val availableQuestsRepository: AvailableQuestsRepository,
    private val questRepository: QuestRepository,
    private val mapper: ListUiModelMapper,
): ViewModel() {

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
            when(val result = availableQuestsRepository.getQuests()) {
                is Result.Error -> { displayError(result.error.toString()) }
                is Result.Success -> {
                    val availableQuests = result.data
                    fetchQuestsAndUpdateUi(availableQuests)
                }
            }
        }
    }

    private suspend fun fetchQuestsAndUpdateUi(
        availableQuests: AvailableQuestsRepository.Response
    ) = withContext(Dispatchers.IO) {
        when (val questsResult = questRepository.getQuests(availableQuests.questIds())) {
            is Result.Error -> { displayError(questsResult.error.toString()) }
            is Result.Success -> {
                withContext(Dispatchers.Main) {
                    _uiState.update { mapper.map(availableQuests, questsResult.data) }
                }
            }
        }
    }

    private fun AvailableQuestsRepository.Response.questIds(): Set<Int> {
        val ids = mutableSetOf<Int>()
        this.categories.forEach { ids.addAll(it.questsIds) }
        return ids
    }

    private suspend fun displayError(message: String) = withContext(Dispatchers.Main) {
        _uiState.update {
            UiModel.Error(message)
        }
    }
}

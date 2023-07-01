package xyz.argent.candidateassessment.ui.views.tokens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.argent.candidateassessment.App
import xyz.argent.candidateassessment.data.model.TokenResponse
import xyz.argent.candidateassessment.data.repository.TokensRepository
import xyz.argent.candidateassessment.data.util.Result
import xyz.argent.candidateassessment.data.util.Result.Error
import xyz.argent.candidateassessment.data.util.Result.Loading
import xyz.argent.candidateassessment.data.util.Result.Success
import xyz.argent.candidateassessment.data.util.asResult

class TokensViewModel(
    tokensRepository: TokensRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")
    val searchUiState: StateFlow<TokensUiState> =
        tokensRepository.getTopTokens()
            .asResult()
            .map(::handleResponse)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = TokensUiState.Loading
            )

    private fun handleResponse(result: Result<List<TokenResponse>>) =
        when (result) {
            is Loading -> TokensUiState.Loading
            is Success -> {
                if (result.data.isNotEmpty()) {
                    TokensUiState.Success(result.data)
                } else {
                    TokensUiState.EmptyResult
                }
            }

            is Error -> TokensUiState.LoadFailed
        }

    fun onSearchQueryChanged(myQuery: String) {
        savedStateHandle[SEARCH_QUERY] = myQuery
    }


    /**
     * Factory for [TokensViewModel] that takes [TokensRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val tokensRepository = application.dependencies.tokensRepository
                val savedStateHandle = SavedStateHandle()
                TokensViewModel(
                    tokensRepository = tokensRepository,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}

sealed interface TokensUiState {
    object Loading : TokensUiState
    object EmptyQuery : TokensUiState
    object EmptyResult : TokensUiState
    object LoadFailed : TokensUiState

    data class Success(val tokens: List<TokenResponse>) : TokensUiState
    object SearchNotReady : TokensUiState
}

private const val SEARCH_QUERY = "searchQuery"

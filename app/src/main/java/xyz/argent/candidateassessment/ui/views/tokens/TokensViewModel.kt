package xyz.argent.candidateassessment.ui.views.tokens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.argent.candidateassessment.App
import xyz.argent.candidateassessment.data.repository.balance.BalanceRepository
import xyz.argent.candidateassessment.data.repository.token.TokensRepository
import xyz.argent.candidateassessment.data.util.Result.Error
import xyz.argent.candidateassessment.data.util.Result.Loading
import xyz.argent.candidateassessment.data.util.Result.Success
import xyz.argent.candidateassessment.data.util.asResult

class TokensViewModel(
    private val savedStateHandle: SavedStateHandle,
    val balanceRepository: BalanceRepository,
    val tokensRepository: TokensRepository
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResultState: StateFlow<TokensUiState> = searchQuery
        .flatMapLatest(::getTokenBalance)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TokensUiState.Loading
        )

    private suspend fun getTokenBalance(query: String): Flow<TokensUiState> {
        if (query.isEmpty()) return flowOf(TokensUiState.EmptyQuery)
        val tokenAddress = tokensRepository.getAddressBy(token = query)
        return balanceRepository.getTokenBalance(address = tokenAddress)
            .asResult()
            .map {
                when (it) {
                    is Success -> TokensUiState.Success(
                        TokenResult(
                            name = query,
                            balance = it.data.result
                        )
                    )

                    is Loading -> TokensUiState.Loading
                    is Error -> TokensUiState.LoadingFailed(it.exception?.message ?: "Error")
                }
            }
    }

    fun onSearchQueryChanged(myQuery: String) {
        savedStateHandle[SEARCH_QUERY] = myQuery
    }

    /**
     * Factory for [TokensViewModel] that takes
     * [TokensRepository] & [SavedStateHandle] as dependencies.
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val tokensRepository = application.dependencies.tokensRepository
                val savedStateHandle = SavedStateHandle()
                val balanceRepository = application.dependencies.balanceRepository
                TokensViewModel(
                    savedStateHandle = savedStateHandle,
                    tokensRepository = tokensRepository,
                    balanceRepository = balanceRepository
                )
            }
        }
    }
}

sealed interface TokensUiState {
    object Loading : TokensUiState
    object EmptyQuery : TokensUiState
    object EmptyResponse : TokensUiState
    object SearchNotReady : TokensUiState
    data class LoadingFailed(val error: String) : TokensUiState
    data class Success(val tokenResult: TokenResult) : TokensUiState
}

private const val SEARCH_QUERY = "searchQuery"

data class TokenResult(
    val name: String,
    val balance: String
)
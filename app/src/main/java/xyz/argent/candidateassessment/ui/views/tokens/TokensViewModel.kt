package xyz.argent.candidateassessment.ui.views.tokens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.argent.candidateassessment.App
import xyz.argent.candidateassessment.data.model.TokenResponse
import xyz.argent.candidateassessment.data.repository.balance.BalanceRepository
import xyz.argent.candidateassessment.data.repository.token.TokensRepository
import xyz.argent.candidateassessment.data.util.Constants
import xyz.argent.candidateassessment.data.util.Result
import xyz.argent.candidateassessment.data.util.Result.Error
import xyz.argent.candidateassessment.data.util.Result.Loading
import xyz.argent.candidateassessment.data.util.Result.Success
import xyz.argent.candidateassessment.data.util.asResult
import xyz.argent.candidateassessment.domain.GetTokenAddressUseCase
import xyz.argent.candidateassessment.util.formatBalance

class TokensViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val tokensRepository: TokensRepository,
    private val balanceRepository: BalanceRepository,
    private val getTokenAddressUseCase: GetTokenAddressUseCase
) : ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")
    private val tokens = mutableListOf<TokenResponse>()

    val topTokensState: StateFlow<TokensUiState> = tokensRepository.getTopTokens()
        .asResult()
        .map(::handleTopTokensResponse)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TokensUiState.Loading
        )

    private fun handleTopTokensResponse(result: Result<List<TokenResponse>>) = when (result) {
        is Success -> {
            tokens.apply { clear(); addAll(result.data) }
            TokensUiState.TopTokensSuccess
        }

        is Loading -> TokensUiState.Loading
        is Error -> TokensUiState.Error(result.exception?.message ?: "Error")
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResultState: StateFlow<TokensUiState> = searchQuery
        .debounce(500L)
        .distinctUntilChanged()
        .flatMapLatest(::getTokenBalance)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TokensUiState.Loading
        )

    private fun getTokenBalance(query: String): Flow<TokensUiState> {
        if (query.isEmpty()) return flowOf(TokensUiState.EmptyQuery)
        val tokenAddress = getTokenAddressUseCase(tokens = tokens, query = query)

        return balanceRepository.getTokenBalance(
            contractAddress = tokenAddress ?: return flowOf(TokensUiState.Error("Error")),
            address = Constants.walletAddress,
            apiKey = Constants.etherscanApiKey
        ).asResult()
            .map {
                when (it) {
                    is Success -> {
                        val balance = it.data.result.formatBalance()
                        TokensUiState.Success(balance)
                    }

                    is Loading -> TokensUiState.Loading
                    is Error -> TokensUiState.Error(it.exception?.message ?: "Error")
                }
            }
    }

    fun onSearchClick(myQuery: String) {
        savedStateHandle[SEARCH_QUERY] = myQuery
    }

    fun onClear() {
        savedStateHandle[SEARCH_QUERY] = ""
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
                val getTokenAddressUseCase = application.dependencies.getTokenAddressUseCase
                TokensViewModel(
                    savedStateHandle = savedStateHandle,
                    tokensRepository = tokensRepository,
                    balanceRepository = balanceRepository,
                    getTokenAddressUseCase = getTokenAddressUseCase
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
    object TopTokensSuccess : TokensUiState
    data class Error(val error: String) : TokensUiState
    data class Success(val balance: String) : TokensUiState
}

private const val SEARCH_QUERY = "searchQuery"
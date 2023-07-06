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
import kotlinx.coroutines.flow.launchIn
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
import xyz.argent.candidateassessment.domain.FormatBalanceUseCase
import xyz.argent.candidateassessment.domain.GetTokensAddressUseCase

class TokensViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val tokensRepository: TokensRepository,
    private val balanceRepository: BalanceRepository,
    private val getTokensAddressUseCase: GetTokensAddressUseCase,
    private val formatBalanceUseCase: FormatBalanceUseCase
) : ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")
    private lateinit var tokens: List<TokenResponse>

    init {
        fetchTopTokens()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResultState: StateFlow<TokensUiState> = searchQuery
        .debounce(500L)
        .distinctUntilChanged()
        .flatMapLatest {
            if (it.isEmpty()) flowOf(TokensUiState.EmptyQuery) else getTokenBalance(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TokensUiState.EmptyQuery
        )

    private fun getTokenBalance(query: String): Flow<TokensUiState> {
        val tokensAddress = getTokensAddressUseCase(tokens = tokens, query = query)
        return balanceRepository.getTokensBalance(
            tokensAddresses = tokensAddress,
            symbols = tokens.map { it.symbol }
        )
            .asResult()
            .map { handleTokensBalanceResponse(it) }
    }

    private fun handleTokensBalanceResponse(
        result: Result<Map<String, String>>
    ) = when (result) {
        is Success -> {
            if (result.data.isEmpty()) TokensUiState.EmptyResponse
            else {
                val tokensMap = formatBalanceUseCase(
                    tokens = tokens.toSet(),
                    tokensBalances = result.data
                )

                println("TokensMap: $tokensMap")

                TokensUiState.Success(
                    tokens = tokensMap.map { (token, balance) ->
                        val imgUrl = Constants.BASE_URL + tokens.first { it.symbol == token }.image
                        Token(
                            symbol = result.data.keys.first { it == token },
                            imgUrl = imgUrl,
                            balance = balance.toString()
                        )
                    }
                )
            }
        }

        is Loading -> TokensUiState.Loading
        is Error -> TokensUiState.Error(result.exception?.message ?: "Error")
    }

    private fun fetchTopTokens() {
        tokensRepository.getTopTokens()
            .asResult()
            .map {
                when (it) {
                    is Success -> tokens = it.data
                    is Loading -> TokensUiState.Loading
                    is Error -> TokensUiState.Error(it.exception?.message ?: "Error")
                }
            }.launchIn(viewModelScope)
    }

    fun onSearchClick(myQuery: String) {
        savedStateHandle[SEARCH_QUERY] = myQuery
    }

    fun onClear() {
        savedStateHandle[SEARCH_QUERY] = ""
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val tokensRepository = application.dependencies.tokensRepository
                val savedStateHandle = SavedStateHandle()
                val balanceRepository = application.dependencies.balanceRepository
                val getTokenAddressUseCase = application.dependencies.getTokensAddressUseCase
                val formatBalanceUseCase = application.dependencies.formatBalanceUseCase

                TokensViewModel(
                    savedStateHandle = savedStateHandle,
                    tokensRepository = tokensRepository,
                    balanceRepository = balanceRepository,
                    getTokensAddressUseCase = getTokenAddressUseCase,
                    formatBalanceUseCase = formatBalanceUseCase
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
    data class Error(val error: String) : TokensUiState
    data class Success(val tokens: List<Token>) : TokensUiState
}

private const val SEARCH_QUERY = "searchQuery"
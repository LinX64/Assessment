package xyz.argent.candidateassessment.ui.views.tokens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.argent.candidateassessment.data.model.TokenResponse
import xyz.argent.candidateassessment.ui.views.components.TokenRow
import xyz.argent.candidateassessment.ui.views.tokens.components.SearchToolbar

@Composable
internal fun TokensRoute(
    onBackClick: () -> Unit
) {
    val tokensViewModel: TokensViewModel =
        viewModel(factory = TokensViewModel.Factory)
    val tokensUiState by tokensViewModel.searchUiState.collectAsStateWithLifecycle()
    val searchQuery by tokensViewModel.searchQuery.collectAsStateWithLifecycle()
    TokensScreen(
        tokensUiState = tokensUiState,
        searchQuery = searchQuery,
        onBackClick = onBackClick,
        onSearchQueryChanged = tokensViewModel::onSearchQueryChanged
    )
}

@Composable
fun TokensScreen(
    modifier: Modifier = Modifier,
    tokensUiState: TokensUiState,
    searchQuery: String = "",
    onBackClick: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTriggered: (String) -> Unit = {},
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchToolbar(
            onBackClick = onBackClick,
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery
        )

        when (tokensUiState) {
            is TokensUiState.Loading -> {
                println("Loading")
            }

            is TokensUiState.Success -> {
                val tokens = tokensUiState.tokens
                SearchResultBody(tokens = tokens)
            }

            is TokensUiState.EmptyResult -> {
                println("Empty result")
            }

            TokensUiState.EmptyQuery -> {

            }
            TokensUiState.LoadFailed -> {
            }
            TokensUiState.SearchNotReady -> {

            }
        }
    }
}

@Composable
fun SearchResultBody(
    modifier: Modifier = Modifier,
    tokens: List<TokenResponse>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tokens) { token ->
            TokenRow(token = token)
        }
    }
}

@Preview
@Composable
private fun TokensPreview() {
    TokensScreen(
        tokensUiState = TokensUiState.Success(
            listOf(
                TokenResponse(
                    "USDC",
                    "USD Coin",
                    "https://assets.coingecko.com/coins/images/6319/small/USD_Coin_icon",
                    decimals = 6454,
                    image = "https://assets.coingecko.com/coins/images/6319/small/USD_Coin_icon",
                )
            )
        ),
        searchQuery = "USDC",
        onBackClick = {},
        onSearchQueryChanged = {},
        onSearchTriggered = {}
    )
}
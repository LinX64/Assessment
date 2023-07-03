package xyz.argent.candidateassessment.ui.views.tokens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.argent.candidateassessment.ui.views.components.TokenRow
import xyz.argent.candidateassessment.ui.views.tokens.components.SearchToolbar

@Composable
internal fun TokensRoute(
    onBackClick: () -> Unit
) {
    val tokensViewModel: TokensViewModel =
        viewModel(factory = TokensViewModel.Factory)
    val searchResultState by tokensViewModel.searchResultState.collectAsStateWithLifecycle()
    val topTokensState by tokensViewModel.topTokensState.collectAsStateWithLifecycle()
    val searchQuery by tokensViewModel.searchQuery.collectAsStateWithLifecycle()

    TokensScreen(
        searchResultState = searchResultState,
        searchQuery = searchQuery,
        onBackClick = onBackClick,
        onSearchQueryChanged = tokensViewModel::onSearchQueryChanged
    )
}

@Composable
fun TokensScreen(
    modifier: Modifier = Modifier,
    searchResultState: TokensUiState,
    searchQuery: String = "",
    onBackClick: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTriggered: (String) -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchToolbar(
            onBackClick = onBackClick,
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery
        )

        when (searchResultState) {
            TokensUiState.EmptyQuery -> {
                println("Empty query")
            }

            TokensUiState.EmptyResponse -> {
                println("Empty response")
            }

            TokensUiState.Loading -> {
                println("Loading")
            }

            TokensUiState.SearchNotReady -> {
                println("Search not ready")
            }

            is TokensUiState.LoadingFailed -> {
                println("Loading failed: ${searchResultState.error}")
            }

            is TokensUiState.Success -> {
                SearchResultBody(balance = searchResultState.balance)
            }

            else -> {}
        }
    }
}

@Composable
fun SearchResultBody(
    modifier: Modifier = Modifier,
    balance: String
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(key = balance) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                TokenRow(balance = balance)
            }
        }
    }
}

@Preview
@Composable
private fun TokensPreview() {
    /*TokensScreen(
        tokensUiState = TokensUiState.Success(
            listOf(
                TokenResponse(
                    "USDC",
                    "USD Coin",
                    "USD_Coin",
                    decimals = 6454.0,
                    image = "ADDDS",
                )
            )
        ),
        searchQuery = "USDC",
        onBackClick = {},
        onSearchQueryChanged = {},
        onSearchTriggered = {}
    )*/
}
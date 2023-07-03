package xyz.argent.candidateassessment.ui.views.tokens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.argent.candidateassessment.ui.views.components.BaseCenterColumn
import xyz.argent.candidateassessment.ui.views.tokens.components.ProgressBar
import xyz.argent.candidateassessment.ui.views.tokens.components.SearchBarView

@Composable
fun TokensRoute() {
    val tokensViewModel: TokensViewModel = viewModel(factory = TokensViewModel.Factory)
    val searchResultState by tokensViewModel.searchResultState.collectAsStateWithLifecycle()
    val topTokensState by tokensViewModel.topTokensState.collectAsStateWithLifecycle()
    // Can't remove this because the ViewModel is not initialized yet, can be fixed by adding hiltViewModel()

    TokensScreen(
        searchResultState = searchResultState,
        onSearchClick = tokensViewModel::onSearchClick,
        onClear = tokensViewModel::onClear
    )
}

@Composable
internal fun TokensScreen(
    searchResultState: TokensUiState,
    onSearchClick: (String) -> Unit = {},
    onClear: () -> Unit
) {
    val shouldTheTextBeShown = remember { mutableStateOf(true) }

    SearchBarView(
        searchUiState = searchResultState,
        onSearchClick = onSearchClick,
        isSearchViewActive = { shouldTheTextBeShown.value = !it },
        onClear = onClear
    )

    if (shouldTheTextBeShown.value) {
        DefaultContent()
    }

    when (searchResultState) {
        is TokensUiState.EmptyResponse -> EmptyResponseView()
        is TokensUiState.Loading -> ProgressBar()
        is TokensUiState.Error -> ErrorView()

        else -> Unit
    }
}

@Composable
fun ErrorView() {
    BaseCenterColumn {
        Text(
            text = "Something went wrong, please try again or check your internet connection",
            textAlign = TextAlign.Center,
             modifier = Modifier
                .padding(horizontal = 16.dp)
                .semantics { contentDescription = "Something went wrong" }
        )
    }
}

@Composable
private fun EmptyResponseView() {
    BaseCenterColumn {
        Text(
            text = "No results found",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .height(24.dp)
                .semantics { contentDescription = "No results found" }
        )
    }
}

@Composable
private fun LoadingFailedView() {
    BaseCenterColumn {
        Text(
            modifier = Modifier
                .semantics { contentDescription = "Something went wrong" }
                .height(24.dp),
            text = "Something went wrong",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DefaultContent() {
    BaseCenterColumn {
        Text(
            modifier = Modifier.semantics { contentDescription = "Search for a token" },
            text = "Please enter a token name to search for",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "i.e. USDT, ETH, BTC")
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TokensPreview() {
    TokensScreen(
        searchResultState = TokensUiState.Success(balance = "1000"),
        onClear = {},
    )
}
package xyz.argent.candidateassessment.ui.views.tokens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.argent.candidateassessment.ui.views.tokens.components.DefaultContent
import xyz.argent.candidateassessment.ui.views.tokens.components.EmptyResponseView
import xyz.argent.candidateassessment.ui.views.tokens.components.ErrorView
import xyz.argent.candidateassessment.ui.views.tokens.components.ProgressBar
import xyz.argent.candidateassessment.ui.views.tokens.components.SearchBarView

@Composable
fun TokensRoute() {
    val tokensViewModel: TokensViewModel = viewModel(factory = TokensViewModel.Factory)
    val searchResultState by tokensViewModel.searchResultState.collectAsStateWithLifecycle()

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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TokensPreview() {
    TokensScreen(
        searchResultState = TokensUiState.Success(emptyList()),
        onClear = {},
    )
}
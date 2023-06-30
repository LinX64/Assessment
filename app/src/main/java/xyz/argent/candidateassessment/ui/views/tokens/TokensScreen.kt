package xyz.argent.candidateassessment.ui.views.tokens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import xyz.argent.candidateassessment.ui.views.tokens.components.SearchToolbar

@Composable
internal fun TokensRoute(
    onBackClick: () -> Unit
) {
    TokensScreen(
        onBackClick = onBackClick,
    )
}

@Composable
fun TokensScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTriggered: (String) -> Unit = {},
    searchQuery: String = ""
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchToolbar(
            onBackClick = onBackClick,
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery
        )
    }
}

@Preview
@Composable
private fun TokensPreview() {
    TokensScreen()
}
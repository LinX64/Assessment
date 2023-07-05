package xyz.argent.candidateassessment.ui.views.tokens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.ui.views.components.TokenRow
import xyz.argent.candidateassessment.ui.views.tokens.TokensUiState

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
fun SearchBarView(
    modifier: Modifier = Modifier,
    searchUiState: TokensUiState,
    onSearchClick: (String) -> Unit,
    isSearchViewActive: (Boolean) -> Unit,
    onClear: () -> Unit
) {
    val query = remember { mutableStateOf("") }
    val isQueryEmpty = query.value.isEmpty()
    var active by rememberSaveable { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchClick(query.value)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            }
            .semantics { contentDescription = "SearchScreen"; isContainer = true }
    ) {
        SearchBar(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            query = query.value,
            onQueryChange = {
                query.value = it
                onSearchClick(it)
            },
            onSearch = onSearchClick,
            placeholder = { Text(text = "Search...") },
            leadingIcon = { LeadingIcon() },
            trailingIcon = {
                if (isQueryEmpty.not()) {
                    TrailingIcon(onClear = {
                        active = false
                        query.value = ""
                        onClear()
                    })
                }
            },
            active = active.also(isSearchViewActive),
            onActiveChange = { active = it },
            colors = SearchBarDefaults.colors(
                dividerColor = Color.Transparent
            )
        ) {
            LazyVerticalGrid(
                modifier = modifier.fillMaxWidth(),
                columns = GridCells.Adaptive(300.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                onboardingView(searchUiState)
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

private fun LazyGridScope.onboardingView(searchUiState: TokensUiState) = when (searchUiState) {
    is TokensUiState.Loading,
    is TokensUiState.EmptyQuery,
    is TokensUiState.SearchNotReady -> Unit

    is TokensUiState.Success -> {
        items(
            count = searchUiState.tokens.size,
            key = { index -> searchUiState.tokens[index].symbol }
        ) { index ->
            TokenRow(
                token = searchUiState.tokens[index]
            )
        }
    }

    else -> Unit
}

@Composable
private fun LeadingIcon() {
    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = null,
        tint = Color.Gray
    )
}

@Composable
private fun TrailingIcon(onClear: () -> Unit) {
    IconButton(
        onClick = { onClear() }
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null
        )
    }
}

@Composable
@Preview
private fun SearchBarViewPreview() {
    SearchBarView(
        searchUiState = TokensUiState.EmptyResponse,
        onSearchClick = {},
        isSearchViewActive = {},
    ) {}
}
package xyz.argent.candidateassessment

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import xyz.argent.candidateassessment.ui.views.tokens.TokensScreen
import xyz.argent.candidateassessment.ui.views.tokens.TokensUiState

class TokensSearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenSearchScreenIsLaunched_thenSearchBarIsDisplayed() {
        with(composeTestRule) {
            setContent {
                BoxWithConstraints {
                    TokensScreen(
                        searchResultState = TokensUiState.Success(balance = "0.0"),
                        onSearchClick = {},
                        onClear = {}
                    )
                }
            }

            onNodeWithText("SearchScreen", ignoreCase = true)
                .assertExists()
        }
    }

    @Test
    fun whenSearchScreenIsLaunched_thenShowEmptyState() {
        with(composeTestRule) {
            setContent {
                BoxWithConstraints {
                    val shouldShowText = true
                    TokensScreen(
                        searchResultState = TokensUiState.EmptyResponse,
                        onSearchClick = {},
                        onClear = {}
                    )
                }
            }

            onNodeWithText("Search for a token", ignoreCase = true)
                .assertExists()
        }
    }

    @Test
    fun whenSearchScreenIsLaunched_thenShowLoadingState() {
        with(composeTestRule) {
            setContent {
                BoxWithConstraints {
                    TokensScreen(
                        searchResultState = TokensUiState.Loading,
                        onSearchClick = {},
                        onClear = {}
                    )
                }
            }
        }
    }

    @Test
    fun whenSearchScreenIsLaunched_thenCheckIfTextIsDisplayed() {
        with(composeTestRule) {
            setContent {
                BoxWithConstraints {
                    TokensScreen(
                        searchResultState = TokensUiState.EmptyResponse,
                        onSearchClick = {},
                        onClear = {},
                    )
                }
            }

            onNodeWithText("No results found", ignoreCase = true)
                .assertExists()
        }
    }
}
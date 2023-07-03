package xyz.argent.candidateassessment

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import xyz.argent.candidateassessment.ui.views.IntroScreen

class IntroScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_IntroScreenIsDisplayed_Then_TheUserCanSwipeToTheNextScreen() {
        with(composeTestRule) {
            setContent {
                BoxWithConstraints {
                    IntroScreen(
                        onErcButtonClick = { }
                    )
                }
            }

            onNodeWithText("ERC20 TOKENS", ignoreCase = true)
                .performClick()
        }
    }

    @Test
    fun when_IntroScreenIsDisplayed_Then_TheUserCanSeeTheWalletAddress() {
        with(composeTestRule) {
            setContent {
                BoxWithConstraints {
                    IntroScreen(
                        onErcButtonClick = { }
                    )
                }
            }

            onNodeWithText("wallet address", ignoreCase = true)
                .assertExists()
        }
    }
}
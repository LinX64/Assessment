package xyz.argent.candidateassessment.ui.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.ui.views.components.BaseCenterColumn

@Composable
internal fun IntroRoute(
    onButtonClick: () -> Unit
) {
    IntroScreen(onErcButtonClick = onButtonClick)
}

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    onErcButtonClick: () -> Unit
) {
    BaseCenterColumn {
        Text(
            text = "Wallet Address",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = modifier.padding(horizontal = 16.dp).semantics { contentDescription = "wallet address" },
            text = "0x1234567890abcdef1234567890abcdef12345678",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.semantics { contentDescription = "ERC20 TOKENS" },
            onClick = { onErcButtonClick() }) {
            Text(text = "ERC20 TOKENS")
        }
    }
}

@Preview
@Composable
private fun IntroPreview() {
    IntroScreen(onErcButtonClick = {})
}
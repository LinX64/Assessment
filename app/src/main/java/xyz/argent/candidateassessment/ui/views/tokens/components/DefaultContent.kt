package xyz.argent.candidateassessment.ui.views.tokens.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.ui.views.components.BaseCenterColumn

@Composable
fun DefaultContent() {
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
package xyz.argent.candidateassessment.ui.views.tokens.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.ui.views.components.BaseCenterColumn

@Composable
fun EmptyResponseView() {
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
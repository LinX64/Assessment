package xyz.argent.candidateassessment.ui.views.tokens.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.ui.views.components.BaseCenterColumn

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
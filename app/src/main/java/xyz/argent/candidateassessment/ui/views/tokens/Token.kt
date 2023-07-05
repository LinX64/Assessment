package xyz.argent.candidateassessment.ui.views.tokens

import androidx.compose.runtime.Stable

@Stable
data class Token(
    val symbol: String,
    val balance: String
)
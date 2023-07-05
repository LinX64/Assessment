package xyz.argent.candidateassessment.ui.views.tokens

import androidx.compose.runtime.Stable

@Stable
data class Token(
    val symbol: String,
    val imgUrl: String,
    val balance: String
)
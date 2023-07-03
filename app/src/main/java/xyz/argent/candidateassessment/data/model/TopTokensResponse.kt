package xyz.argent.candidateassessment.data.model

import androidx.compose.runtime.Stable

@Stable
data class TopTokensResponse(
    val tokens: List<TokenResponse>
)

@Stable
data class TokenResponse(
    val address: String,
    val name: String?,
    val symbol: String?,
    val decimals: Double?,
    val image: String?,
)
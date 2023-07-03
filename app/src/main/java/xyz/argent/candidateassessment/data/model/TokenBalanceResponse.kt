package xyz.argent.candidateassessment.data.model

import androidx.compose.runtime.Stable

@Stable
data class TokenBalanceResponse(
    val status: Long,
    val message: String,
    val result: String
)
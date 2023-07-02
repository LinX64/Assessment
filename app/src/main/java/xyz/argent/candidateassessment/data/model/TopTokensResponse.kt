package xyz.argent.candidateassessment.data.model

data class TopTokensResponse(
    val tokens: List<TokenResponse>
)

data class TokenResponse(
    val address: String,
    val name: String?,
    val symbol: String?,
    val decimals: Double?,
    val image: String?,
)
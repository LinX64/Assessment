package xyz.argent.candidateassessment.domain

import xyz.argent.candidateassessment.data.model.TokenResponse

class GetTokenAddressUseCase {

    operator fun invoke(
        tokens: List<TokenResponse>,
        query: String
    ): String? = tokens.find { token ->
        token.symbol?.contains(query, ignoreCase = true) == true
    }?.address
}
package xyz.argent.candidateassessment.domain

import xyz.argent.candidateassessment.data.model.TokenResponse

class GetTokensAddressUseCase {

    operator fun invoke(
        tokens: List<TokenResponse>,
        query: String
    ): List<String> {
        val filteredTokens = tokens.filter { token ->
            token.symbol?.contains(query, ignoreCase = true) == true
        }

        return filteredTokens.map { it.address }
    }
}
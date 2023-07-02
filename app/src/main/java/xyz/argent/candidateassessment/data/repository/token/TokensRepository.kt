package xyz.argent.candidateassessment.data.repository.token

import kotlinx.coroutines.flow.Flow
import xyz.argent.candidateassessment.data.model.TokenResponse

interface TokensRepository {
    fun getTopTokens(): Flow<List<TokenResponse>>
    suspend fun getAddressBy(token: String): String
}


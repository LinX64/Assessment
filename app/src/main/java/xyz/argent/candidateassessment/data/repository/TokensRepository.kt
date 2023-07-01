package xyz.argent.candidateassessment.data.repository

import kotlinx.coroutines.flow.Flow
import xyz.argent.candidateassessment.data.model.TokenResponse

interface TokensRepository {
    fun getTopTokens(): Flow<List<TokenResponse>>
}

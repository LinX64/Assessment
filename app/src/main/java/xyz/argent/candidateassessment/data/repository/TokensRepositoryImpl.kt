package xyz.argent.candidateassessment.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.argent.candidateassessment.data.network.tokenRegistry.EthExplorerApi
import xyz.argent.candidateassessment.data.model.TokenResponse

class TokensRepositoryImpl(
    private val ethExplorerApi: EthExplorerApi
) : TokensRepository {

    override fun getTopTokens(): Flow<List<TokenResponse>> = flow {
        val topTokens = ethExplorerApi.getTopTokens().tokens
        emit(topTokens)
    }
}
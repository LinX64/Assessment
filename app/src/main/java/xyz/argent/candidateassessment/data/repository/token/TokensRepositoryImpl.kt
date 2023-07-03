package xyz.argent.candidateassessment.data.repository.token

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import xyz.argent.candidateassessment.data.model.TokenResponse
import xyz.argent.candidateassessment.data.network.tokenRegistry.EthExplorerApi

class TokensRepositoryImpl(
    private val ethExplorerApi: EthExplorerApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TokensRepository {

    override fun getTopTokens(): Flow<List<TokenResponse>> = flow {
        val topTokens = ethExplorerApi.getTopTokens().tokens
        emit(topTokens)
    }.flowOn(ioDispatcher)
}
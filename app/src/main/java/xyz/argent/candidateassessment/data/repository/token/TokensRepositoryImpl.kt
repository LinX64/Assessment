package xyz.argent.candidateassessment.data.repository.token

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
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

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override suspend fun getAddressBy(token: String): String {
        val topTokens = withContext(ioDispatcher) { getTopTokens() }
        return topTokens
            .debounce(500)
            .mapLatest { it.toSet() }
            .distinctUntilChanged()
            .flatMapLatest {
                val address = it.find {
                    it.name?.contains(token, ignoreCase = true) == true
                }?.address
                flowOf(address ?: "Error finding token address")
            }.first()
    }
}
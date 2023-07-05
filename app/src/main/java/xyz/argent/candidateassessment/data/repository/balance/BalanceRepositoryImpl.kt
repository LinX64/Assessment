package xyz.argent.candidateassessment.data.repository.balance

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import xyz.argent.candidateassessment.data.model.TokenBalanceResponse
import xyz.argent.candidateassessment.data.network.balanceRetriever.EtherscanApi
import xyz.argent.candidateassessment.data.util.Constants

class BalanceRepositoryImpl(
    private val etherscanApi: EtherscanApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BalanceRepository {

    override fun getTokenBalance(
        contractAddress: String,
        address: String,
        apiKey: String
    ): Flow<TokenBalanceResponse> = flow {
        val response = etherscanApi.getTokenBalance(contractAddress, address, apiKey)
        emit(response)
    }.flowOn(ioDispatcher)

    override fun getTokensBalance(tokensAddresses: List<String>): Flow<Map<String, String>> = flow {
        val tokenBalancesMap = mutableMapOf<String, String>()

        tokensAddresses.forEach { address ->
            val tokenBalance = etherscanApi.getTokenBalance(
                contractAddress = address,
                address = Constants.walletAddress,
                apiKey = Constants.etherscanApiKey
            ).result

            tokenBalancesMap[address] = tokenBalance
        }

        emit(tokenBalancesMap)
    }.flowOn(ioDispatcher)

}
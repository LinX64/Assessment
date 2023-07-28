package xyz.argent.candidateassessment.data.repository.balance

import kotlinx.coroutines.flow.Flow
import xyz.argent.candidateassessment.data.model.TokenBalanceResponse





























interface BalanceRepository {
    fun getTokenBalance(
        contractAddress: String,
        address: String,
        apiKey: String
    ): Flow<TokenBalanceResponse>

    fun getTokensBalance(
        tokensAddresses: List<String>,
        symbols: List<String?>
    ): Flow<Map<String, String>>
}

























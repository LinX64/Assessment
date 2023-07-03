package xyz.argent.candidateassessment.data.repository.balance

import kotlinx.coroutines.flow.Flow
import xyz.argent.candidateassessment.data.model.TokenBalanceResponse
import xyz.argent.candidateassessment.data.util.Constants

interface BalanceRepository {
    fun getTokenBalance(
        contractAddress: String,
        address: String,
        apiKey: String
    ): Flow<TokenBalanceResponse>
}


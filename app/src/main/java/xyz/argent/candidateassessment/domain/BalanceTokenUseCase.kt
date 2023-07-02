package xyz.argent.candidateassessment.domain

import xyz.argent.candidateassessment.data.repository.balance.BalanceRepository
import xyz.argent.candidateassessment.data.repository.token.TokensRepository

class BalanceTokenUseCase(
    private val tokenRepository: TokensRepository,
    private val balanceRepository: BalanceRepository
) {
   /* suspend operator fun invoke(token: String): String {
        val getTopTokens = tokenRepository.getTopTokens()
        val balance = balanceRepository.getTokenBalance()



    }*/
}
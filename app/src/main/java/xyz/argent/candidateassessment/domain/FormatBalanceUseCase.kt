package xyz.argent.candidateassessment.domain

import xyz.argent.candidateassessment.data.model.TokenResponse
import java.math.BigDecimal

class FormatBalanceUseCase {

    operator fun invoke(
        tokens: Set<TokenResponse>,
        tokensBalances: Map<String, String>
    ): Map<String, Double> {

        /**
         * map of token symbols to decimals
         * e.g. "ETH" to 18.0
         */
        val tokensSymbolsMap = tokens
            .filter { it.symbol?.isNotEmpty() == true }
            .associate { it.symbol to handleDecimal(it.decimals) }
            .toMap()

        return divideBalances(tokensBalances, tokensSymbolsMap)
    }

    private fun handleDecimal(decimal: Double?): Double? =
        decimal?.let { "%.1f".format(it) }?.toDouble()

    /**
     * Map of token symbols to balances
     * e.g. "USDT" to 100.0
     * The division is being done here based on the decimals of the token.
     */
    private fun divideBalances(
        tokensBalances: Map<String, String>,
        tokensMap: Map<String?, Double?>
    ): Map<String, Double> {
        return tokensBalances.mapNotNull { (token, balance) ->
            try {
                val decimals = tokensMap[token] ?: 0.0
                token to divideBalance(balance.toDouble(), decimals)
            } catch (e: NumberFormatException) {
                null // Skip this token balance if it cannot be parsed
            }
        }.toMap()
    }

    /**
     * Divides the balance by the decimals of the token
     * e.g. 100.0 / 18.0 = 0.0000000000000001
     */


    private fun divideBalance(balance: Double, decimals: Double): Double {
        if (decimals == 0.0) return balance

        val dividedBalance = BigDecimal(balance)
            .divide(BigDecimal(decimals), BigDecimal.ROUND_HALF_UP)
            .setScale(6, BigDecimal.ROUND_HALF_UP)

        return dividedBalance.toDouble()
    }
}
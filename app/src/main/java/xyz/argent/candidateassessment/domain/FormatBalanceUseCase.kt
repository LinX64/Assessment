package xyz.argent.candidateassessment.domain

import android.util.Log
import xyz.argent.candidateassessment.data.model.TokenResponse
import kotlin.math.pow

class FormatBalanceUseCase {

    operator fun invoke(
        tokens: Set<TokenResponse>,
        tokenBalances: Map<String, String>
    ): Map<String?, Double?> {

        Log.d("FormatBalanceUseCase", "invoke: tokens: $tokens")
        Log.d("FormatBalanceUseCase", "invoke: tokenBalances: $tokenBalances")

        /**
         * map of token symbols to decimals
         * e.g. "ETH" to 18.0
         */
        val tokensMap = tokens
            .filter { it.symbol?.isNotEmpty() == true }
            .associate { it.symbol to handleDecimal(it.decimals) }

        /**
         * map of token symbols to balances
         * e.g. "ETH" to 0.000000000000000001
         * The division is being done here based on the decimals of the token.
         */
        return divideBalances(
            tokensMap = tokensMap,
            tokenBalances = tokenBalances
        )
            .mapValues { it.value }
            .toMap()
    }

    private fun handleDecimal(decimal: Double?): Double? =
        decimal?.let { "%.1f".format(it) }?.toDouble()

    private fun divideBalances(
        tokenBalances: Map<String, String>,
        tokensMap: Map<String?, Double?>
    ): Map<String, Double> {
        return tokenBalances.map { (token, balance) ->
            val decimals = tokensMap[token] ?: 0.0
            token to divideBalance(balance.toDouble(), decimals)
        }.toMap()
    }

    private fun divideBalance(balance: Double, decimals: Double): Double {
        return (balance / 10.0.pow(decimals))
    }
}
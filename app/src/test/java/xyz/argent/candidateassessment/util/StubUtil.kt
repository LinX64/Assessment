package xyz.argent.candidateassessment.util

import xyz.argent.candidateassessment.data.model.TokenBalanceResponse
import xyz.argent.candidateassessment.data.model.TokenResponse

object StubUtil {

    fun getStubTokens(): List<TokenResponse> {
        val stubTokens = mutableListOf<TokenResponse>()
        repeat(10) {
            val items = TokenResponse(
                address = "0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2",
                name = "WETH",
                symbol = "WETH",
                decimals = 18.0,
                image = "https://ethplorer.io/images/weth.png"
            )
            stubTokens.add(items)
        }
        return stubTokens
    }

    fun getStubBalance(): TokenBalanceResponse {
        return TokenBalanceResponse(
            status = 1,
            message = "OK",
            result = "1.0"
        )
    }
}

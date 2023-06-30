package xyz.argent.candidateassessment.tokenRegistry

import retrofit2.http.GET
import retrofit2.http.Query

interface EthExplorerApi {

    @GET("/getTopTokens")
    suspend fun getTopTokens(
        @Query("limit") limit: Int,
        @Query("apiKey") apiKey: String
    ): TopTokensResponse

    data class TopTokensResponse(
        val tokens: List<TokenResponse>
    )

    data class TokenResponse(
        val address: String,
        val name: String?,
        val symbol: String?,
        val decimals: Long?,
        val image: String?,
    )
}
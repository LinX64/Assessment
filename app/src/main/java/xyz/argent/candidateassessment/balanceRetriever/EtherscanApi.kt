package xyz.argent.candidateassessment.balanceRetriever

import retrofit2.http.GET
import retrofit2.http.Query


interface EtherscanApi {

    @GET("/api?module=account&action=tokenbalance&tag=latest")
    suspend fun getTokenBalance(
        @Query("contractaddress") contractAddress: String,
        @Query("address") address: String,
        @Query("apiKey") apiKey: String
    ): TokenBalanceResponse

    data class TokenBalanceResponse(
        val status: Long,
        val message: String,
        val result: String
    )
}
package xyz.argent.candidateassessment.data.balanceRetriever

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.argent.candidateassessment.data.model.TokenBalanceResponse

interface EtherscanApi {

    @GET("/api?module=account&action=tokenbalance&tag=latest")
    suspend fun getTokenBalance(
        @Query("contractaddress") contractAddress: String,
        @Query("address") address: String,
        @Query("apiKey") apiKey: String
    ): TokenBalanceResponse
}
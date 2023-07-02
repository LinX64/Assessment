package xyz.argent.candidateassessment.data.network.balanceRetriever

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.argent.candidateassessment.data.model.TokenBalanceResponse
import xyz.argent.candidateassessment.data.util.Constants

interface EtherscanApi {

    @GET(Constants.GET_TOKEN_BALANCE)
    suspend fun getTokenBalance(
        @Query("contractaddress") contractAddress: String,
        @Query("address") address: String,
        @Query("apiKey") apiKey: String
    ): TokenBalanceResponse
}
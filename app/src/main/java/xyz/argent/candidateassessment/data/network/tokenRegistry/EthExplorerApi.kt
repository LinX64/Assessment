package xyz.argent.candidateassessment.data.network.tokenRegistry

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.argent.candidateassessment.data.model.TopTokensResponse

interface EthExplorerApi {

    @GET("/getTopTokens")
    suspend fun getTopTokens(
        @Query("limit") limit: Int = 100,
        @Query("apiKey") apiKey: String = "freekey"
    ): TopTokensResponse
}
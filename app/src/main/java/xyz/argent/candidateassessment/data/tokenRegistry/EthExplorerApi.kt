package xyz.argent.candidateassessment.data.tokenRegistry

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.argent.candidateassessment.data.model.TopTokensResponse

interface EthExplorerApi {

    @GET("/getTopTokens")
    suspend fun getTopTokens(
        @Query("limit") limit: Int,
        @Query("apiKey") apiKey: String
    ): TopTokensResponse
}
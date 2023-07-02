package xyz.argent.candidateassessment.data.network.tokenRegistry

import retrofit2.http.GET
import retrofit2.http.Query
import xyz.argent.candidateassessment.data.model.TopTokensResponse
import xyz.argent.candidateassessment.data.util.Constants

interface EthExplorerApi {

    @GET(Constants.GET_TOP_TOKENS)
    suspend fun getTopTokens(
        @Query("limit") limit: Int = 100,
        @Query("apiKey") apiKey: String = Constants.ethExplorerApiKey
    ): TopTokensResponse
}
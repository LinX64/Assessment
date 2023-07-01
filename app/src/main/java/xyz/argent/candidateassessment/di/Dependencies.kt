package xyz.argent.candidateassessment.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.argent.candidateassessment.data.network.balanceRetriever.EtherscanApi
import xyz.argent.candidateassessment.data.network.tokenRegistry.EthExplorerApi
import xyz.argent.candidateassessment.data.repository.TokensRepository
import xyz.argent.candidateassessment.data.repository.TokensRepositoryImpl

interface DependenciesContainer {
    val tokensRepository: TokensRepository
}

/** Some manual dependency injection to simplify here */
class Dependencies : DependenciesContainer {

    private val etherscanApi: EtherscanApi
    private val ethExplorerApi: EthExplorerApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        val sharedOkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        ethExplorerApi = Retrofit.Builder()
            .baseUrl("https://api.ethplorer.io/")
            .client(sharedOkHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EthExplorerApi::class.java)

        etherscanApi = Retrofit.Builder()
            .baseUrl("https://api.etherscan.io/")
            .client(sharedOkHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EtherscanApi::class.java)

        // TODO: Use caching for the API calls
    }

    override val tokensRepository: TokensRepository by lazy {
        TokensRepositoryImpl(ethExplorerApi)
    }
}


package xyz.argent.candidateassessment.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.argent.candidateassessment.App
import xyz.argent.candidateassessment.data.network.balanceRetriever.EtherscanApi
import xyz.argent.candidateassessment.data.network.tokenRegistry.EthExplorerApi
import xyz.argent.candidateassessment.data.repository.balance.BalanceRepository
import xyz.argent.candidateassessment.data.repository.balance.BalanceRepositoryImpl
import xyz.argent.candidateassessment.data.repository.token.TokensRepository
import xyz.argent.candidateassessment.data.repository.token.TokensRepositoryImpl
import xyz.argent.candidateassessment.domain.GetTokenAddressUseCase


interface DependenciesContainer {
    val tokensRepository: TokensRepository
    val balanceRepository: BalanceRepository

    val getTokenAddressUseCase: GetTokenAddressUseCase
}

/** Some manual dependency injection to simplify here */
class Dependencies(
    appContext: App
) : DependenciesContainer {

    private val etherscanApi: EtherscanApi
    private val ethExplorerApi: EthExplorerApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }

        val onlineInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val maxAge = 60
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }
        val offlineInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (!isInternetAvailable(appContext)) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(appContext.cacheDir, cacheSize)

        val sharedOkHttpClient = OkHttpClient.Builder()
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(myCache)
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
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var isConnected = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }


    override val tokensRepository: TokensRepository by lazy {
        TokensRepositoryImpl(ethExplorerApi)
    }
    override val balanceRepository: BalanceRepository by lazy {
        BalanceRepositoryImpl(etherscanApi)
    }
    override val getTokenAddressUseCase: GetTokenAddressUseCase by lazy {
        GetTokenAddressUseCase()
    }
}


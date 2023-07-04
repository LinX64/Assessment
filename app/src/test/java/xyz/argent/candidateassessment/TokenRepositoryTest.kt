package xyz.argent.candidateassessment

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import xyz.argent.candidateassessment.data.model.TopTokensResponse
import xyz.argent.candidateassessment.data.network.tokenRegistry.EthExplorerApi
import xyz.argent.candidateassessment.data.repository.token.TokensRepository
import xyz.argent.candidateassessment.data.repository.token.TokensRepositoryImpl
import xyz.argent.candidateassessment.util.StubUtil

class TokenRepositoryTest {

    private lateinit var tokensRepository: TokensRepository
    private lateinit var ethExplorerApi: EthExplorerApi

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val tokens = StubUtil.getStubTokens()

    @Before
    fun setup() {
        ethExplorerApi = Mockito.mock(EthExplorerApi::class.java)
        tokensRepository = TokensRepositoryImpl(ethExplorerApi = ethExplorerApi)
    }

    @Test
    fun `When getTokens is called, then the tokens are retrieved`() = testScope.runTest {
        val topTokenResponse = TopTokensResponse(tokens)

        val methodCall = ethExplorerApi.getTopTokens()
        whenever(methodCall).thenReturn(topTokenResponse)

        tokensRepository.getTopTokens().test {
            awaitItem().size shouldBeEqualTo 10
            awaitComplete()
        }
    }

    @Test
    fun `When getTokens is called, then check if WETH is in the list`() = testScope.runTest {
        val topTokenResponse = TopTokensResponse(tokens)

        val methodCall = ethExplorerApi.getTopTokens()
        whenever(methodCall).thenReturn(topTokenResponse)

        tokensRepository.getTopTokens().test {
            awaitItem()[0].symbol?.contains("WETH") shouldBeEqualTo true
            awaitComplete()
        }
    }
}
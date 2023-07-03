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
import xyz.argent.candidateassessment.data.network.balanceRetriever.EtherscanApi
import xyz.argent.candidateassessment.data.repository.balance.BalanceRepository
import xyz.argent.candidateassessment.data.repository.balance.BalanceRepositoryImpl
import xyz.argent.candidateassessment.util.StubUtil

class BalanceRepositoryTest {

    private lateinit var balanceRepository: BalanceRepository
    private lateinit var etherscanApi: EtherscanApi

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val tokenBalanceStub = StubUtil.getStubBalance()

    @Before
    fun setup() {
        etherscanApi = Mockito.mock(EtherscanApi::class.java)
        balanceRepository = BalanceRepositoryImpl(etherscanApi = etherscanApi)
    }

    @Test
    fun `When getTokenBalance() is called, then the balance was retrieved`() = testScope.runTest {
        val methodCall = etherscanApi.getTokenBalance(
            contractAddress = "0x0d8775f648430679a709e98d2b0cb6250d2887ef",
            address = "0x000000",
            apiKey = "YourApiKeyToken"
        )
        whenever(methodCall).thenReturn(tokenBalanceStub)

        balanceRepository.getTokenBalance(
            contractAddress = "0x0d8775f648430679a709e98d2b0cb6250d2887ef",
            address = "0x000000",
            apiKey = "YourApiKeyToken"
        ).test {
            awaitItem().result shouldBeEqualTo "1.0"
            awaitComplete()
        }
    }

    @Test
    fun `When getTokenBalance() is called, then check if call was successful`() =
        testScope.runTest {
            val methodCall = etherscanApi.getTokenBalance(
                contractAddress = "0x0d8775f648430679a709e98d2b0cb6250d2887ef",
                address = "0x000000",
                apiKey = "YourApiKeyToken"
            )
            whenever(methodCall).thenReturn(tokenBalanceStub)

            balanceRepository.getTokenBalance(
                contractAddress = "0x0d8775f648430679a709e98d2b0cb6250d2887ef",
                address = "0x000000",
                apiKey = "YourApiKeyToken"
            ).test {
                awaitItem().status shouldBeEqualTo 1
                awaitComplete()
            }
        }
}
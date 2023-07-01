package xyz.argent.candidateassessment.ui.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.data.model.TokenResponse
import xyz.argent.candidateassessment.ui.views.tokens.SearchResultBody

@Composable
fun TokenRow(
    modifier: Modifier = Modifier,
    token: TokenResponse
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            USDCBalanceRow(token = token)

            Spacer(modifier = Modifier.height(8.dp))

            USDTBalanceRow(token = token)
        }
    }
}

@Composable
private fun USDCBalanceRow(token: TokenResponse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "USDC")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "234234324")
    }
}

@Composable
private fun USDTBalanceRow(token: TokenResponse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = token.name.toString())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = token.symbol.toString())
    }
}

@Composable
@Preview
fun SearchResultBodyPreview() {
    SearchResultBody(
        tokens = listOf(
            TokenResponse(
                "USDC",
                "234234324",
                "0x1234",
                56464,
                "0x1234",
            ),
            TokenResponse(
                "USDC",
                "234234324",
                "0x1234",
                56464,
                "0x1234",
            )
        )
    )
}
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.ui.views.tokens.Token

@Composable
fun TokenRow(
    modifier: Modifier = Modifier,
    token: Token
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        border = CardDefaults.outlinedCardBorder(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            USDCBalanceRow(balance = token.balance.toString())

            Spacer(modifier = Modifier.height(8.dp))

            USDTBalanceRow(name = token.symbol)
        }
    }
}

@Composable
private fun USDCBalanceRow(balance: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "USDC Balance:")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = balance)
    }
}

@Composable
private fun USDTBalanceRow(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "USDT Balance:")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name)
    }
}

@Composable
@Preview
private fun TokenRowPreview() {
    TokenRow(
        token = listOf(
            Token(
                balance = 100.0,
                symbol = "USDC"
            )
        )[0]
    )
}
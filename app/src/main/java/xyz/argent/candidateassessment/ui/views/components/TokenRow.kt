package xyz.argent.candidateassessment.ui.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier
                    .size(42.dp)
                    .padding(start = 3.dp)
                    .clip(CircleShape)
                    .background(color = Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = token.symbol,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                USDCBalanceRow(balance = token.balance)

                Spacer(modifier = Modifier.height(8.dp))

                USDTBalanceRow()
            }
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
        Text(
            text = "USDC Balance:",
            fontSize = MaterialTheme.typography.bodySmall.fontSize
        )

        Spacer(modifier = Modifier.height(8.dp))

        val setColorByValue = if (balance == "0" || balance.matches(Regex("^0\\.0+$"))) {
            Color.Red
        } else {
            Color(0xFF1EAF25)
        }

        Text(
            text = balance,
            color = setColorByValue,
            fontSize = MaterialTheme.typography.bodySmall.fontSize
        )
    }
}

@Composable
private fun USDTBalanceRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "USDT Balance:",
            fontSize = MaterialTheme.typography.bodySmall.fontSize
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "0",
            fontSize = MaterialTheme.typography.bodySmall.fontSize
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun TokenRowPreview() {
    TokenRow(
        token = listOf(
            Token(
                symbol = "USDC",
                balance = ""
            )
        )[0]
    )
}
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
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.ui.views.tokens.TokenResult

@Composable
fun TokenRow(
    modifier: Modifier = Modifier,
    balance: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            USDCBalanceRow(balance = balance)

            Spacer(modifier = Modifier.height(8.dp))

            USDTBalanceRow(name = balance)
        }
    }
}

@Composable
private fun USDCBalanceRow(balance: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "USDC Balance:")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "234234324")
    }
}

@Composable
private fun USDTBalanceRow(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "USDT Balance:")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name)
    }
}
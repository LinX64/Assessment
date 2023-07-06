package xyz.argent.candidateassessment.ui.views.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import xyz.argent.candidateassessment.R
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
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(token.imgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.baseline_cloud_24),
                modifier = modifier
                    .clip(CircleShape)
                    .padding(start = 8.dp)
                    .size(32.dp)
            )

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
                imgUrl = "",
                balance = ""
            )
        )[0]
    )
}
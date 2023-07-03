package xyz.argent.candidateassessment.util

import java.math.BigDecimal

fun String.formatBalance(decimalPlaces: Int = 18, exchangeRate: Double = 1.0): String {
    val balance = this.toBigDecimal()
        .divide(BigDecimal.TEN.pow(decimalPlaces))

    val usdcBalance = balance.multiply(exchangeRate.toBigDecimal())

    return String.format("$%,.${decimalPlaces}f", usdcBalance)
}



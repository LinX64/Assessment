package xyz.argent.candidateassessment.util

private const val MWEI_CONVERSION_FACTOR = 1_000_000_000_000.0

fun formatWeiToMWei(wei: Long): String {
    val mWei = wei.toDouble() / MWEI_CONVERSION_FACTOR
    return "%.6f".format(mWei)
}


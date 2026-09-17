package com.example.borowallet.model

enum class AssetCategory(val displayName: String) {
    ALL("All Assets"),
    CRYPTO("Crypto"),
    STOCKS("Stocks"),
    ETFS("ETFs & Nasdaq")
}

data class HistoricalPoint(
    val time: String,
    val value: Double
)

data class Asset(
    val id: String,
    val name: String,
    val symbol: String,
    val type: AssetCategory,
    val currentPrice: Double,
    val change24h: Double,
    val sharesOrTokens: Double,
    val unitLabel: String,
    val iconType: String = "symbol", // "symbol" or "icon"
    val iconValue: String,
    val iconColor: Long,
    val iconBg: Long,
    val description: String = "",
    val marketCap: String = "",
    val volume24h: String = "",
    val high52w: Double = 0.0,
    val low52w: Double = 0.0,
    val historicalData: List<HistoricalPoint> = emptyList()
) {
    val totalValue: Double
        get() = currentPrice * sharesOrTokens
}

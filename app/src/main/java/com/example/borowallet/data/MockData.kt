package com.example.borowallet.data

import com.example.borowallet.model.*

object MockData {
    val initialProfile = UserProfile(
        name = "Alexandre Sterling",
        email = "alexandre@sterling.capital",
        referralCode = "BORO-8821",
        cashBalance = 14250.00,
        isBiometricsEnabled = true,
        preferredCurrency = "USD",
        membershipTier = "Private Member",
        isFdicInsured = true,
        friendsInvited = 4,
        totalRewardsEarnedBtc = 100.0
    )

    val initialAssets = listOf(
        Asset(
            id = "btc",
            name = "Bitcoin",
            symbol = "BTC",
            type = AssetCategory.CRYPTO,
            currentPrice = 96420.50,
            change24h = 4.25,
            sharesOrTokens = 0.842,
            unitLabel = "BTC",
            iconType = "symbol",
            iconValue = "₿",
            iconColor = 0xFFF7931A,
            iconBg = 0x26F7931A,
            description = "The world's premier decentralized digital currency and store of value.",
            marketCap = "$1.89 Trillion",
            volume24h = "$48.2 Billion",
            high52w = 99800.00,
            low52w = 38500.00,
            historicalData = listOf(
                HistoricalPoint("00:00", 94100.0),
                HistoricalPoint("04:00", 94800.0),
                HistoricalPoint("08:00", 95300.0),
                HistoricalPoint("12:00", 95900.0),
                HistoricalPoint("16:00", 96200.0),
                HistoricalPoint("20:00", 96420.5)
            )
        ),
        Asset(
            id = "aapl",
            name = "Apple Inc.",
            symbol = "AAPL",
            type = AssetCategory.STOCKS,
            currentPrice = 234.80,
            change24h = 1.65,
            sharesOrTokens = 45.0,
            unitLabel = "Shares",
            iconType = "symbol",
            iconValue = "",
            iconColor = 0xFF1A1C1F,
            iconBg = 0xFFF4F5F6,
            description = "Global technology leader in personal computing, smartphones, and digital ecosystems.",
            marketCap = "$3.56 Trillion",
            volume24h = "$12.4 Billion",
            high52w = 237.20,
            low52w = 164.08,
            historicalData = listOf(
                HistoricalPoint("09:30", 231.0),
                HistoricalPoint("11:00", 232.5),
                HistoricalPoint("13:00", 233.8),
                HistoricalPoint("15:00", 234.2),
                HistoricalPoint("16:00", 234.8)
            )
        ),
        Asset(
            id = "qqq",
            name = "Invesco QQQ",
            symbol = "QQQ",
            type = AssetCategory.ETFS,
            currentPrice = 512.40,
            change24h = 0.95,
            sharesOrTokens = 20.0,
            unitLabel = "Shares",
            iconType = "symbol",
            iconValue = "Q",
            iconColor = 0xFF0052FF,
            iconBg = 0x260052FF,
            description = "Tracks the Nasdaq-100 index covering the 100 largest non-financial innovative companies.",
            marketCap = "$288 Billion",
            volume24h = "$8.1 Billion",
            high52w = 518.00,
            low52w = 395.20,
            historicalData = listOf(
                HistoricalPoint("09:30", 507.0),
                HistoricalPoint("11:00", 509.4),
                HistoricalPoint("13:00", 510.8),
                HistoricalPoint("15:00", 511.9),
                HistoricalPoint("16:00", 512.4)
            )
        ),
        Asset(
            id = "eth",
            name = "Ethereum",
            symbol = "ETH",
            type = AssetCategory.CRYPTO,
            currentPrice = 3680.20,
            change24h = 3.10,
            sharesOrTokens = 3.5,
            unitLabel = "ETH",
            iconType = "symbol",
            iconValue = "Ξ",
            iconColor = 0xFF627EEA,
            iconBg = 0x26627EEA,
            description = "Leading smart-contract platform powering decentralized finance, tokens, and Web3 apps.",
            marketCap = "$442 Billion",
            volume24h = "$22.8 Billion",
            high52w = 4090.00,
            low52w = 2140.00,
            historicalData = listOf(
                HistoricalPoint("00:00", 3550.0),
                HistoricalPoint("06:00", 3590.0),
                HistoricalPoint("12:00", 3640.0),
                HistoricalPoint("18:00", 3665.0),
                HistoricalPoint("24:00", 3680.2)
            )
        ),
        Asset(
            id = "nvda",
            name = "NVIDIA Corp.",
            symbol = "NVDA",
            type = AssetCategory.STOCKS,
            currentPrice = 142.60,
            change24h = 2.85,
            sharesOrTokens = 30.0,
            unitLabel = "Shares",
            iconType = "symbol",
            iconValue = "N",
            iconColor = 0xFF76B900,
            iconBg = 0x2676B900,
            description = "Pioneer in accelerated computing and undisputed semiconductor standard for generative AI.",
            marketCap = "$3.48 Trillion",
            volume24h = "$31.5 Billion",
            high52w = 149.75,
            low52w = 45.20,
            historicalData = listOf(
                HistoricalPoint("09:30", 138.5),
                HistoricalPoint("11:30", 140.0),
                HistoricalPoint("13:30", 141.2),
                HistoricalPoint("15:30", 142.1),
                HistoricalPoint("16:00", 142.6)
            )
        ),
        Asset(
            id = "voo",
            name = "Vanguard S&P 500",
            symbol = "VOO",
            type = AssetCategory.ETFS,
            currentPrice = 548.90,
            change24h = 0.72,
            sharesOrTokens = 12.0,
            unitLabel = "Shares",
            iconType = "symbol",
            iconValue = "V",
            iconColor = 0xFFC00000,
            iconBg = 0x26C00000,
            description = "Low-cost index fund benchmarked to the 500 leading publicly traded US corporations.",
            marketCap = "$510 Billion",
            volume24h = "$4.9 Billion",
            high52w = 552.10,
            low52w = 420.50,
            historicalData = listOf(
                HistoricalPoint("09:30", 545.0),
                HistoricalPoint("12:00", 546.8),
                HistoricalPoint("14:30", 548.1),
                HistoricalPoint("16:00", 548.9)
            )
        )
    )

    val timeframePoints: Map<String, List<HistoricalPoint>> = mapOf(
        "1D" to listOf(
            HistoricalPoint("09:30", 112400.0),
            HistoricalPoint("11:00", 113100.0),
            HistoricalPoint("12:30", 112800.0),
            HistoricalPoint("14:00", 113900.0),
            HistoricalPoint("15:00", 114300.0),
            HistoricalPoint("16:00", 114820.0)
        ),
        "1W" to listOf(
            HistoricalPoint("Mon", 108500.0),
            HistoricalPoint("Tue", 109800.0),
            HistoricalPoint("Wed", 111200.0),
            HistoricalPoint("Thu", 112400.0),
            HistoricalPoint("Fri", 114820.0)
        ),
        "1M" to listOf(
            HistoricalPoint("W1", 102000.0),
            HistoricalPoint("W2", 105400.0),
            HistoricalPoint("W3", 109100.0),
            HistoricalPoint("W4", 114820.0)
        ),
        "1Y" to listOf(
            HistoricalPoint("Q1", 78000.0),
            HistoricalPoint("Q2", 89500.0),
            HistoricalPoint("Q3", 98200.0),
            HistoricalPoint("Q4", 114820.0)
        ),
        "ALL" to listOf(
            HistoricalPoint("2021", 42000.0),
            HistoricalPoint("2022", 55000.0),
            HistoricalPoint("2023", 72000.0),
            HistoricalPoint("2024", 94000.0),
            HistoricalPoint("Current", 114820.0)
        )
    )

    val newsArticles = listOf(
        NewsArticle(
            id = "news-1",
            title = "Federal Reserve signals policy easing as inflation prints below target",
            summary = "Macro liquidity expectations provide tailwinds for risk-on assets across equities and digital commodities.",
            category = "Macro",
            source = "Bloomberg Terminal",
            timeAgo = "18m ago",
            imageUrl = "https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?w=600&auto=format&fit=crop&q=80",
            imageAlt = "Financial market chart display",
            readTime = "3 min read",
            sentiment = "Bullish",
            keyTakeaways = listOf(
                "Rate cut expectations increased for upcoming FOMC meeting",
                "Treasury yields softened across the 2-year and 10-year curve",
                "Equities and cryptocurrencies responded with broad-based green moves"
            )
        ),
        NewsArticle(
            id = "news-2",
            title = "Global institutional inflows into spot Bitcoin and Ethereum ETFs surpass $4.2B",
            summary = "Sovereign wealth funds and top asset allocators accelerate balanced multi-asset allocation strategies.",
            category = "Crypto",
            source = "CoinDesk Alpha",
            timeAgo = "42m ago",
            imageUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?w=600&auto=format&fit=crop&q=80",
            imageAlt = "Cryptocurrency digital tokens",
            readTime = "4 min read",
            sentiment = "Very Bullish",
            keyTakeaways = listOf(
                "Record weekly net inflows into regulated custodial funds",
                "Adoption curve steepens among pension allocators",
                "Long-term illiquid supply hits historic highs"
            )
        ),
        NewsArticle(
            id = "news-3",
            title = "Big Tech cloud revenues surge 31% driven by next-gen generative AI inference",
            summary = "Semiconductor chip demand and hyper-scale infrastructure investments show sustained multi-year momentum.",
            category = "Equities",
            source = "Financial Times",
            timeAgo = "1h ago",
            imageUrl = "https://images.unsplash.com/photo-1526304640581-d334cdbbf45e?w=600&auto=format&fit=crop&q=80",
            imageAlt = "Modern microchip and hardware",
            readTime = "5 min read",
            sentiment = "Positive",
            keyTakeaways = listOf(
                "Accelerated silicon spending forecast revised upwards",
                "Enterprise software monetization timelines accelerated",
                "Nasdaq 100 constituents expand operational margins"
            )
        ),
        NewsArticle(
            id = "news-4",
            title = "Index rebalancing boosts passive ETF capital allocations across US industrials",
            summary = "Quarterly liquidity shifts drive record institutional trading volumes across primary equity desks.",
            category = "ETFs",
            source = "Wall Street Journal",
            timeAgo = "2h ago",
            imageUrl = "https://images.unsplash.com/photo-1590283603385-17ffb3a7f29f?w=600&auto=format&fit=crop&q=80",
            imageAlt = "Trading screens and financial graphs",
            readTime = "2 min read",
            sentiment = "Neutral",
            keyTakeaways = listOf(
                "Passive mutual funds rebalance quarterly weights",
                "Record volume recorded in closing cross auctions",
                "Tight spreads maintained across major ETFs"
            )
        )
    )

    val notifications = listOf(
        NotificationItem(
            id = "notif-1",
            title = "Price Alert: Bitcoin surpassed $96,000",
            description = "BTC is up +4.25% in the last 24 hours. Your portfolio gained approximately $3,450.",
            timeAgo = "10m ago",
            isRead = false,
            type = NotificationType.ALERT
        ),
        NotificationItem(
            id = "notif-2",
            title = "Referral Bonus Credited",
            description = "Marcus K. completed identity verification. $25.00 in BTC has been added to your vault.",
            timeAgo = "2h ago",
            isRead = false,
            type = NotificationType.REWARD
        ),
        NotificationItem(
            id = "notif-3",
            title = "Dividend Payment Received",
            description = "Apple Inc. (AAPL) distributed a quarterly cash dividend of $11.25 into your cash account.",
            timeAgo = "1d ago",
            isRead = true,
            type = NotificationType.TRANSACTION
        ),
        NotificationItem(
            id = "notif-4",
            title = "Biometric Passkey Configured",
            description = "Device security hardware authenticated your Android device for instant zero-lag authorization.",
            timeAgo = "3d ago",
            isRead = true,
            type = NotificationType.SECURITY
        )
    )

    val benchmarks = listOf(
        MarketBenchmark("S&P 500", "SPX", "5,983.25", 0.65),
        MarketBenchmark("Nasdaq 100", "NDX", "21,180.40", 1.12),
        MarketBenchmark("Bitcoin", "BTC", "$96,420", 4.25),
        MarketBenchmark("Gold", "XAU", "$2,748.10", 0.38)
    )
}

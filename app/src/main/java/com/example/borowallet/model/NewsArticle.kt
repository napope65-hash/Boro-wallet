package com.example.borowallet.model

data class NewsArticle(
    val id: String,
    val title: String,
    val summary: String,
    val category: String,
    val source: String,
    val timeAgo: String,
    val imageUrl: String,
    val imageAlt: String = "",
    val readTime: String = "3 min read",
    val sentiment: String = "Bullish",
    val keyTakeaways: List<String> = emptyList()
)

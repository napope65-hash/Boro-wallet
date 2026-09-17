package com.example.borowallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borowallet.data.MockData
import com.example.borowallet.model.Asset
import com.example.borowallet.model.AssetCategory
import com.example.borowallet.model.NewsArticle
import com.example.borowallet.ui.components.*
import com.example.borowallet.ui.theme.BoroPrimaryVariant

@Composable
fun WalletScreen(
    assets: List<Asset>,
    totalPortfolioValue: Double,
    isBalanceHidden: Boolean,
    isLiveTickerActive: Boolean,
    selectedCategory: AssetCategory,
    searchQuery: String,
    selectedTimeframe: String,
    sortOption: String,
    onToggleBalance: () -> Unit,
    onToggleLive: () -> Unit,
    onSelectCategory: (AssetCategory) -> Unit,
    onSearchChange: (String) -> Unit,
    onSelectTimeframe: (String) -> Unit,
    onSortChange: (String) -> Unit,
    onBuySellClick: () -> Unit,
    onTransferClick: () -> Unit,
    onSelectAsset: (Asset) -> Unit,
    onSelectArticle: (NewsArticle) -> Unit,
    onInviteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSortMenu by remember { mutableStateOf(false) }

    // Filter by category and search
    val filtered = assets.filter { asset ->
        val matchCategory = selectedCategory == AssetCategory.ALL || asset.type == selectedCategory
        val q = searchQuery.trim().lowercase()
        val matchSearch = q.isEmpty() || asset.name.lowercase().contains(q) || asset.symbol.lowercase().contains(q)
        matchCategory && matchSearch
    }

    // Sort
    val sorted = when (sortOption) {
        "value-desc" -> filtered.sortedByDescending { it.totalValue }
        "value-asc" -> filtered.sortedBy { it.totalValue }
        "gain-desc" -> filtered.sortedByDescending { it.change24h }
        "name-asc" -> filtered.sortedBy { it.name }
        else -> filtered
    }

    val historicalPoints = MockData.timeframePoints[selectedTimeframe] ?: MockData.timeframePoints["1D"]!!

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("wallet_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Portfolio Summary
        item {
            PortfolioSummaryCard(
                totalValue = totalPortfolioValue,
                isBalanceHidden = isBalanceHidden,
                isLive = isLiveTickerActive,
                onToggleBalance = onToggleBalance,
                onToggleLive = onToggleLive
            )
        }

        // Interactive Chart
        item {
            PortfolioChartView(
                historicalData = historicalPoints,
                selectedTimeframe = selectedTimeframe,
                onSelectTimeframe = onSelectTimeframe
            )
        }

        // Primary Action Buttons (Buy & Sell, Transfer)
        item {
            PrimaryActionButtons(
                onBuySellClick = onBuySellClick,
                onTransferClick = onTransferClick
            )
        }

        // Referral Banner Card
        item {
            ReferralBannerCard(onInviteClick = onInviteClick)
        }

        // Category Filter Pills
        item {
            CategoryFilterRow(
                selectedCategory = selectedCategory,
                onSelectCategory = onSelectCategory
            )
        }

        // Search Bar & Holdings Header Row
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("wallet_search_field"),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text("Search stocks, crypto, ETFs...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )

                // Holdings Header + Sort button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Holdings",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp
                            )
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = sorted.size.toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Box {
                        TextButton(
                            onClick = { showSortMenu = true },
                            modifier = Modifier.testTag("sort_holdings_button")
                        ) {
                            Text("Sort", color = BoroPrimaryVariant, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Icon(
                                imageVector = Icons.Default.UnfoldMore,
                                contentDescription = "Sort Options",
                                tint = BoroPrimaryVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Total Value (High)") },
                                onClick = { onSortChange("value-desc"); showSortMenu = false },
                                trailingIcon = { if (sortOption == "value-desc") Icon(Icons.Default.Check, null, tint = BoroPrimaryVariant) }
                            )
                            DropdownMenuItem(
                                text = { Text("24h Gainers") },
                                onClick = { onSortChange("gain-desc"); showSortMenu = false },
                                trailingIcon = { if (sortOption == "gain-desc") Icon(Icons.Default.Check, null, tint = BoroPrimaryVariant) }
                            )
                            DropdownMenuItem(
                                text = { Text("Alphabetical (A-Z)") },
                                onClick = { onSortChange("name-asc"); showSortMenu = false },
                                trailingIcon = { if (sortOption == "name-asc") Icon(Icons.Default.Check, null, tint = BoroPrimaryVariant) }
                            )
                            DropdownMenuItem(
                                text = { Text("Total Value (Low)") },
                                onClick = { onSortChange("value-asc"); showSortMenu = false },
                                trailingIcon = { if (sortOption == "value-asc") Icon(Icons.Default.Check, null, tint = BoroPrimaryVariant) }
                            )
                        }
                    }
                }
            }
        }

        // Holdings List Items
        if (sorted.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No assets found for \"$searchQuery\"",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            items(sorted, key = { it.id }) { asset ->
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
                    AssetHoldingItem(
                        asset = asset,
                        isBalanceHidden = isBalanceHidden,
                        onClick = { onSelectAsset(asset) }
                    )
                }
            }
        }

        // Market Intelligence News Section
        item {
            MarketIntelligenceSection(
                articles = MockData.newsArticles,
                onSelectArticle = onSelectArticle
            )
        }
    }
}

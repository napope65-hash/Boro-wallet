package com.example.borowallet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.borowallet.ui.theme.BoroAccentSoft
import com.example.borowallet.ui.theme.BoroPrimaryVariant
import com.example.borowallet.ui.theme.GainGreen
import java.util.Locale

@Composable
fun ExploreScreen(
    assets: List<Asset>,
    onSelectAsset: (Asset) -> Unit,
    onQuickTrade: (Asset) -> Unit,
    modifier: Modifier = Modifier
) {
    val topGainer = assets.maxByOrNull { it.change24h } ?: assets.first()
    val benchmarks = MockData.benchmarks

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("explore_screen"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Market Pulse Title
        item {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Market Pulse",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 28.sp
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ElectricBolt,
                        contentDescription = null,
                        tint = BoroPrimaryVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Global macroeconomic telemetry & sector sentiment",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        // Fear & Greed Sentiment Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("fear_greed_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Crypto & Equities Sentiment",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x1900875A))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "74 • GREED",
                                color = GainGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Progress bar representing market sentiment score 74/100
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.74f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(4.dp))
                                .background(GainGreen)
                        )
                    }

                    Text(
                        text = "Historical liquidity inflows indicate heightened institutional accumulation across digital assets and semiconductor leaders.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }

        // Global Benchmarks 2x2 Grid
        item {
            Text(
                text = "Global Benchmarks",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    BenchmarkCard(benchmark = benchmarks[0], modifier = Modifier.weight(1f))
                    BenchmarkCard(benchmark = benchmarks[1], modifier = Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    BenchmarkCard(benchmark = benchmarks[2], modifier = Modifier.weight(1f))
                    BenchmarkCard(benchmark = benchmarks[3], modifier = Modifier.weight(1f))
                }
            }
        }

        // Top Gainer Today Spotlight Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("top_gainer_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Whatshot,
                                contentDescription = null,
                                tint = BoroPrimaryVariant,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Top Performer Today",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BoroPrimaryVariant
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(BoroAccentSoft)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "+%.2f%%".format(Locale.US, topGainer.change24h),
                                color = BoroPrimaryVariant,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(topGainer.iconBg)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = topGainer.iconValue,
                                    color = Color(topGainer.iconColor),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                            Column {
                                Text(
                                    text = topGainer.name,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "$%,.2f".format(Locale.US, topGainer.currentPrice),
                                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                )
                            }
                        }

                        Button(
                            onClick = { onQuickTrade(topGainer) },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BoroPrimaryVariant,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            modifier = Modifier.testTag("top_gainer_trade_button")
                        ) {
                            Text("Trade", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // Trending Assets List
        item {
            Text(
                text = "Trending Watchlist",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }

        items(assets, key = { "explore-${it.id}" }) { asset ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onSelectAsset(asset) }
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(asset.iconBg)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = asset.iconValue,
                            color = Color(asset.iconColor),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Column {
                        Text(
                            text = asset.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = asset.symbol,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$%,.2f".format(Locale.US, asset.currentPrice),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = BoroPrimaryVariant,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "+%.2f%%".format(Locale.US, asset.change24h),
                            color = BoroPrimaryVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun BenchmarkCard(benchmark: com.example.borowallet.model.MarketBenchmark, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = benchmark.name, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = "+%.2f%%".format(Locale.US, benchmark.change), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = BoroPrimaryVariant)
        }
        Text(text = benchmark.value, fontSize = 16.sp, fontWeight = FontWeight.Black)
    }
}

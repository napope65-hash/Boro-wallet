package com.example.borowallet.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.window.Dialog
import com.example.borowallet.model.Asset
import com.example.borowallet.ui.components.PortfolioChartView
import com.example.borowallet.ui.theme.BoroAccentSoft
import com.example.borowallet.ui.theme.BoroPrimaryVariant
import java.util.Locale

@Composable
fun AssetDetailDialog(
    asset: Asset,
    onDismiss: () -> Unit,
    onOpenBuySell: (Asset) -> Unit,
    onOpenTransfer: (Asset) -> Unit
) {
    var selectedTimeframe by remember { mutableStateOf("1D") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("asset_detail_dialog"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = asset.symbol,
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_asset_detail_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Price and 24h gain
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "$%,.2f".format(Locale.US, asset.currentPrice),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black
                        )
                    )

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(BoroAccentSoft)
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = BoroPrimaryVariant,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "+%.2f%% 24h".format(Locale.US, asset.change24h),
                            color = BoroPrimaryVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Chart
                if (asset.historicalData.isNotEmpty()) {
                    PortfolioChartView(
                        historicalData = asset.historicalData,
                        selectedTimeframe = selectedTimeframe,
                        onSelectTimeframe = { selectedTimeframe = it },
                        modifier = Modifier.padding(horizontal = 0.dp)
                    )
                }

                // Your Position Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Your Position",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Holdings", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("%.4f %s".format(Locale.US, asset.sharesOrTokens, asset.unitLabel), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Total Value", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("$%,.2f".format(Locale.US, asset.totalValue), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }

                // Market Statistics Grid
                Text(
                    text = "Key Statistics",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(label = "Market Cap", value = asset.marketCap.ifBlank { "$1.2T" }, modifier = Modifier.weight(1f))
                    StatBox(label = "24h Volume", value = asset.volume24h.ifBlank { "$14.5B" }, modifier = Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(label = "52W High", value = if (asset.high52w > 0) "$%,.2f".format(Locale.US, asset.high52w) else "$100.00", modifier = Modifier.weight(1f))
                    StatBox(label = "52W Low", value = if (asset.low52w > 0) "$%,.2f".format(Locale.US, asset.low52w) else "$50.00", modifier = Modifier.weight(1f))
                }

                // About
                if (asset.description.isNotBlank()) {
                    Text(
                        text = "About ${asset.name}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    )
                    Text(
                        text = asset.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 22.sp
                        )
                    )
                }

                // Bottom Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                            onOpenBuySell(asset)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("detail_trade_button"),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BoroPrimaryVariant,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Trade", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            onDismiss()
                            onOpenTransfer(asset)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("detail_transfer_button"),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Transfer", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBox(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(10.dp)
    ) {
        Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

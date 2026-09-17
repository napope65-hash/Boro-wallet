package com.example.borowallet.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.borowallet.model.Asset
import com.example.borowallet.ui.theme.BoroAccentSoft
import com.example.borowallet.ui.theme.BoroPrimaryVariant
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuySellDialog(
    assets: List<Asset>,
    initialAsset: Asset?,
    initialTab: String,
    cashBalance: Double,
    onDismiss: () -> Unit,
    onExecuteTrade: (assetId: String, isBuy: Boolean, amountUsd: Double) -> Unit
) {
    var isBuyTab by remember { mutableStateOf(initialTab == "buy") }
    var selectedAsset by remember { mutableStateOf(initialAsset ?: assets.first()) }
    var amountInput by remember { mutableStateOf("") }
    var expandedAssetMenu by remember { mutableStateOf(false) }

    val amountUsd = amountInput.toDoubleOrNull() ?: 0.0
    val tokensCalculated = if (selectedAsset.currentPrice > 0) amountUsd / selectedAsset.currentPrice else 0.0

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("buy_sell_dialog"),
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
                    Text(
                        text = if (isBuyTab) "Buy ${selectedAsset.symbol}" else "Sell ${selectedAsset.symbol}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_buy_sell_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Buy / Sell Tab Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isBuyTab) BoroPrimaryVariant else Color.Transparent)
                            .clickable { isBuyTab = true }
                            .testTag("tab_buy"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Buy",
                            fontWeight = FontWeight.Bold,
                            color = if (isBuyTab) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (!isBuyTab) MaterialTheme.colorScheme.onSurface else Color.Transparent)
                            .clickable { isBuyTab = false }
                            .testTag("tab_sell"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sell",
                            fontWeight = FontWeight.Bold,
                            color = if (!isBuyTab) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Asset Selector
                ExposedDropdownMenuBox(
                    expanded = expandedAssetMenu,
                    onExpandedChange = { expandedAssetMenu = !expandedAssetMenu }
                ) {
                    OutlinedTextField(
                        value = "${selectedAsset.name} (${selectedAsset.symbol}) - $${selectedAsset.currentPrice}",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAssetMenu) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .testTag("asset_dropdown"),
                        shape = RoundedCornerShape(16.dp),
                        label = { Text("Selected Asset") }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAssetMenu,
                        onDismissRequest = { expandedAssetMenu = false }
                    ) {
                        assets.forEach { asset ->
                            DropdownMenuItem(
                                text = { Text("${asset.name} (${asset.symbol}) - $${asset.currentPrice}") },
                                onClick = {
                                    selectedAsset = asset
                                    expandedAssetMenu = false
                                }
                            )
                        }
                    }
                }

                // Amount Input Field
                OutlinedTextField(
                    value = amountInput,
                    onValueChange = { amountInput = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("trade_amount_input"),
                    shape = RoundedCornerShape(16.dp),
                    label = { Text("Amount (USD)") },
                    prefix = { Text("$ ") }
                )

                // Quick Percentage Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(25, 50, 75, 100).forEach { pct ->
                        val label = if (pct == 100) "MAX" else "$pct%"
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    val maxAmount = if (isBuyTab) {
                                        cashBalance
                                    } else {
                                        selectedAsset.totalValue
                                    }
                                    amountInput = "%.2f".format(Locale.US, (maxAmount * pct) / 100.0)
                                }
                                .testTag("trade_chip_$label"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Balance Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (isBuyTab) "Available Cash" else "Available Holdings",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                    Text(
                        text = if (isBuyTab) "$%,.2f".format(Locale.US, cashBalance) else "%.4f %s ($%,.2f)".format(Locale.US, selectedAsset.sharesOrTokens, selectedAsset.unitLabel, selectedAsset.totalValue),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                // Output Estimation Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isBuyTab) "Estimated Received" else "Estimated Payout",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = if (isBuyTab) "%.4f %s".format(Locale.US, tokensCalculated, selectedAsset.unitLabel) else "$%,.2f".format(Locale.US, amountUsd),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Trading Fee", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("$0.00 (Zero Commission)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BoroPrimaryVariant)
                    }
                }

                // Confirm Button
                Button(
                    onClick = {
                        if (amountUsd > 0) {
                            onExecuteTrade(selectedAsset.id, isBuyTab, amountUsd)
                            onDismiss()
                        }
                    },
                    enabled = amountUsd > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("confirm_trade_button"),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isBuyTab) BoroPrimaryVariant else MaterialTheme.colorScheme.onSurface,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isBuyTab) "Confirm Buy ${selectedAsset.symbol}" else "Confirm Sell ${selectedAsset.symbol}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

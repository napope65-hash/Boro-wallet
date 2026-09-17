package com.example.borowallet.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borowallet.model.Asset
import com.example.borowallet.ui.theme.BoroAccentSoft
import com.example.borowallet.ui.theme.BoroPrimaryVariant
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwapScreen(
    assets: List<Asset>,
    onExecuteSwap: (fromId: String, toId: String, amount: Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var fromAsset by remember { mutableStateOf(assets.firstOrNull { it.symbol == "BTC" } ?: assets[0]) }
    var toAsset by remember { mutableStateOf(assets.firstOrNull { it.symbol == "ETH" } ?: assets[1]) }
    var payAmountText by remember { mutableStateOf("") }
    var rotatedAngle by remember { mutableStateOf(0f) }

    var expandedFromMenu by remember { mutableStateOf(false) }
    var expandedToMenu by remember { mutableStateOf(false) }

    val payAmount = payAmountText.toDoubleOrNull() ?: 0.0
    val totalUsdValue = payAmount * fromAsset.currentPrice
    val calculatedReceiveAmount = if (toAsset.currentPrice > 0) totalUsdValue / toAsset.currentPrice else 0.0

    val exchangeRate = if (toAsset.currentPrice > 0) fromAsset.currentPrice / toAsset.currentPrice else 0.0

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("swap_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "Instant Swap",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp
                )
            )
            Text(
                text = "Zero slippage execution across cross-market asset pools",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        // Card Container
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // "You Pay" Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "You Pay",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Bal: %.4f %s".format(Locale.US, fromAsset.sharesOrTokens, fromAsset.unitLabel),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "MAX",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BoroPrimaryVariant,
                                modifier = Modifier
                                    .clickable { payAmountText = fromAsset.sharesOrTokens.toString() }
                                    .testTag("swap_max_button")
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Amount input
                        OutlinedTextField(
                            value = payAmountText,
                            onValueChange = { payAmountText = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            placeholder = { Text("0.0", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("swap_pay_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            textStyle = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        )

                        // From Asset Selector Dropdown
                        Box {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable { expandedFromMenu = true }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .testTag("swap_from_dropdown"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(Color(fromAsset.iconBg)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = fromAsset.iconValue,
                                        color = Color(fromAsset.iconColor),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(text = fromAsset.symbol, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Icon(Icons.Default.ArrowDownward, contentDescription = null, modifier = Modifier.size(14.dp))
                            }

                            DropdownMenu(
                                expanded = expandedFromMenu,
                                onDismissRequest = { expandedFromMenu = false }
                            ) {
                                assets.forEach { asset ->
                                    DropdownMenuItem(
                                        text = { Text("${asset.name} (${asset.symbol})") },
                                        onClick = {
                                            fromAsset = asset
                                            expandedFromMenu = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = "≈ $%,.2f USD".format(Locale.US, totalUsdValue),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Invert Button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable {
                                val temp = fromAsset
                                fromAsset = toAsset
                                toAsset = temp
                                rotatedAngle += 180f
                            }
                            .testTag("invert_swap_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = "Invert Swap Pair",
                            tint = BoroPrimaryVariant,
                            modifier = Modifier
                                .size(22.dp)
                                .rotate(rotatedAngle)
                        )
                    }
                }

                // "You Receive" Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "You Receive (Guaranteed)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Bal: %.4f %s".format(Locale.US, toAsset.sharesOrTokens, toAsset.unitLabel),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (calculatedReceiveAmount > 0) "%.4f".format(Locale.US, calculatedReceiveAmount) else "0.0",
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 12.dp)
                        )

                        // To Asset Selector Dropdown
                        Box {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable { expandedToMenu = true }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .testTag("swap_to_dropdown"),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(Color(toAsset.iconBg)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = toAsset.iconValue,
                                        color = Color(toAsset.iconColor),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(text = toAsset.symbol, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Icon(Icons.Default.ArrowDownward, contentDescription = null, modifier = Modifier.size(14.dp))
                            }

                            DropdownMenu(
                                expanded = expandedToMenu,
                                onDismissRequest = { expandedToMenu = false }
                            ) {
                                assets.forEach { asset ->
                                    DropdownMenuItem(
                                        text = { Text("${asset.name} (${asset.symbol})") },
                                        onClick = {
                                            toAsset = asset
                                            expandedToMenu = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = "≈ $%,.2f USD".format(Locale.US, totalUsdValue),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Exchange Details Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Rate", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            text = "1 %s ≈ %.4f %s".format(Locale.US, fromAsset.symbol, exchangeRate, toAsset.symbol),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Slippage Tolerance", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("0.0% (Guaranteed)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BoroPrimaryVariant)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Network Routing Fee", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Free", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BoroPrimaryVariant)
                    }
                }

                // CTA Button
                Button(
                    onClick = {
                        if (payAmount > 0) {
                            onExecuteSwap(fromAsset.id, toAsset.id, payAmount)
                            payAmountText = ""
                        }
                    },
                    enabled = payAmount > 0 && fromAsset.id != toAsset.id,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("execute_swap_button"),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BoroPrimaryVariant,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (fromAsset.id == toAsset.id) "Select different assets" else "Swap ${fromAsset.symbol} to ${toAsset.symbol}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }

        // Trust badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Protected by Boro Institutional Liquidity Engine",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

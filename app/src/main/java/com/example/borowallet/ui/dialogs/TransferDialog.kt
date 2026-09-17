package com.example.borowallet.ui.dialogs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
fun TransferDialog(
    assets: List<Asset>,
    initialAsset: Asset?,
    initialTab: String,
    onDismiss: () -> Unit,
    onExecuteTransfer: (assetId: String, isSend: Boolean, amountUnits: Double, recipient: String) -> Unit
) {
    var isSendTab by remember { mutableStateOf(initialTab == "send") }
    var selectedAsset by remember { mutableStateOf(initialAsset ?: assets.first()) }
    var recipientInput by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    var copiedAddress by remember { mutableStateOf(false) }
    var expandedAssetMenu by remember { mutableStateOf(false) }

    val amountUnits = amountInput.toDoubleOrNull() ?: 0.0
    val receiveAddress = "0x89F4...${selectedAsset.symbol}9A2B"

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("transfer_dialog"),
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
                        text = if (isSendTab) "Send ${selectedAsset.symbol}" else "Receive ${selectedAsset.symbol}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_transfer_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Send / Receive Toggle
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
                            .background(if (isSendTab) BoroPrimaryVariant else Color.Transparent)
                            .clickable { isSendTab = true }
                            .testTag("tab_send"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Send",
                            fontWeight = FontWeight.Bold,
                            color = if (isSendTab) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (!isSendTab) MaterialTheme.colorScheme.onSurface else Color.Transparent)
                            .clickable { isSendTab = false }
                            .testTag("tab_receive"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Receive",
                            fontWeight = FontWeight.Bold,
                            color = if (!isSendTab) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Asset Selector
                ExposedDropdownMenuBox(
                    expanded = expandedAssetMenu,
                    onExpandedChange = { expandedAssetMenu = !expandedAssetMenu }
                ) {
                    OutlinedTextField(
                        value = "${selectedAsset.name} (${selectedAsset.symbol})",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAssetMenu) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .testTag("transfer_asset_dropdown"),
                        shape = RoundedCornerShape(16.dp),
                        label = { Text("Asset") }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedAssetMenu,
                        onDismissRequest = { expandedAssetMenu = false }
                    ) {
                        assets.forEach { asset ->
                            DropdownMenuItem(
                                text = { Text("${asset.name} (${asset.symbol})") },
                                onClick = {
                                    selectedAsset = asset
                                    expandedAssetMenu = false
                                }
                            )
                        }
                    }
                }

                if (isSendTab) {
                    // Send Tab Content
                    OutlinedTextField(
                        value = recipientInput,
                        onValueChange = { recipientInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("transfer_recipient_input"),
                        shape = RoundedCornerShape(16.dp),
                        label = { Text("Recipient (Address / \$tag / Email)") },
                        placeholder = { Text("e.g. \$alex or 0x...") }
                    )

                    OutlinedTextField(
                        value = amountInput,
                        onValueChange = { amountInput = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("transfer_amount_input"),
                        shape = RoundedCornerShape(16.dp),
                        label = { Text("Amount (${selectedAsset.unitLabel})") },
                        trailingIcon = {
                            TextButton(
                                onClick = { amountInput = selectedAsset.sharesOrTokens.toString() },
                                modifier = Modifier.testTag("transfer_max_button")
                            ) {
                                Text("MAX", fontWeight = FontWeight.Bold, color = BoroPrimaryVariant)
                            }
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Available:", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                        Text("%.4f %s".format(Locale.US, selectedAsset.sharesOrTokens, selectedAsset.unitLabel), style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                    }

                    // Zero network fee note
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Boro Vault Network Fee", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("$0.00 (Instant)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BoroPrimaryVariant)
                    }

                    Button(
                        onClick = {
                            if (amountUnits > 0) {
                                onExecuteTransfer(selectedAsset.id, true, amountUnits, recipientInput)
                                onDismiss()
                            }
                        },
                        enabled = amountUnits > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("confirm_send_button"),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BoroPrimaryVariant,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Send ${selectedAsset.symbol}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                } else {
                    // Receive Tab Content (QR Code Canvas + Deposit Address)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // QR Code Graphic Canvas
                        Box(
                            modifier = Modifier
                                .size(160.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val strokeW = 4.dp.toPx()
                                val cornerSize = 26.dp.toPx()

                                // 4 Corner Finder Patterns
                                drawRoundRect(
                                    color = Color(0xFF1A1C1F),
                                    topLeft = Offset(0f, 0f),
                                    size = Size(cornerSize, cornerSize),
                                    cornerRadius = CornerRadius(6f, 6f),
                                    style = Stroke(strokeW)
                                )
                                drawRoundRect(
                                    color = Color(0xFF1A1C1F),
                                    topLeft = Offset(size.width - cornerSize, 0f),
                                    size = Size(cornerSize, cornerSize),
                                    cornerRadius = CornerRadius(6f, 6f),
                                    style = Stroke(strokeW)
                                )
                                drawRoundRect(
                                    color = Color(0xFF1A1C1F),
                                    topLeft = Offset(0f, size.height - cornerSize),
                                    size = Size(cornerSize, cornerSize),
                                    cornerRadius = CornerRadius(6f, 6f),
                                    style = Stroke(strokeW)
                                )

                                // Center Boro Emblem
                                drawCircle(
                                    color = BoroPrimaryVariant,
                                    radius = 16.dp.toPx(),
                                    center = Offset(size.width / 2f, size.height / 2f)
                                )
                            }
                            Text(
                                text = "B",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                        }

                        Text(
                            text = "Your Boro ${selectedAsset.symbol} Vault Address",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        )

                        // Address pill with copy button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    copiedAddress = true
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp)
                                .testTag("copy_address_pill"),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = receiveAddress,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )

                            Icon(
                                imageVector = if (copiedAddress) Icons.Default.Check else Icons.Default.ContentCopy,
                                contentDescription = "Copy Address",
                                tint = if (copiedAddress) BoroPrimaryVariant else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        if (copiedAddress) {
                            Text(
                                text = "Address copied to clipboard!",
                                color = BoroPrimaryVariant,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                onExecuteTransfer(selectedAsset.id, false, 0.5, "Self-Deposit")
                                onDismiss()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("simulate_receive_button"),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onSurface,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Simulate Receiving Funds", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

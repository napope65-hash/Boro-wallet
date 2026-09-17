package com.example.borowallet.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.borowallet.ui.theme.BoroPrimaryVariant
import java.util.Locale

@Composable
fun CashManagementDialog(
    initialMode: String,
    currentCashBalance: Double,
    onDismiss: () -> Unit,
    onExecuteCashOperation: (isDeposit: Boolean, amount: Double) -> Unit
) {
    var isDeposit by remember { mutableStateOf(initialMode == "deposit") }
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("cash_management_dialog"),
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
                        text = if (isDeposit) "Deposit Cash" else "Withdraw Cash",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_cash_dialog_button")) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Mode Toggle
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
                            .background(if (isDeposit) BoroPrimaryVariant else Color.Transparent)
                            .clickable { isDeposit = true }
                            .testTag("cash_tab_deposit"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Deposit",
                            fontWeight = FontWeight.Bold,
                            color = if (isDeposit) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (!isDeposit) MaterialTheme.colorScheme.onSurface else Color.Transparent)
                            .clickable { isDeposit = false }
                            .testTag("cash_tab_withdraw"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Withdraw",
                            fontWeight = FontWeight.Bold,
                            color = if (!isDeposit) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Linked Account Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = if (isDeposit) "Transfer From" else "Transfer To",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Chase Premier Checking (•••• 4092)",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Instant Settlement via FedNow • FDIC Insured",
                        fontSize = 11.sp,
                        color = BoroPrimaryVariant,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Amount Input
                OutlinedTextField(
                    value = amountInput,
                    onValueChange = { amountInput = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("cash_amount_input"),
                    shape = RoundedCornerShape(16.dp),
                    label = { Text("Amount (USD)") },
                    prefix = { Text("$ ") }
                )

                // Quick Amount Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(100, 500, 1000, 5000).forEach { quickVal ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { amountInput = quickVal.toString() }
                                .testTag("cash_quick_chip_$quickVal"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$$quickVal",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Available Cash Display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Boro Cash Available:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$%,.2f".format(Locale.US, currentCashBalance), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        if (amount > 0) {
                            onExecuteCashOperation(isDeposit, amount)
                            onDismiss()
                        }
                    },
                    enabled = amount > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("confirm_cash_button"),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDeposit) BoroPrimaryVariant else MaterialTheme.colorScheme.onSurface,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isDeposit) "Confirm Deposit $%,.2f".format(Locale.US, amount) else "Confirm Withdrawal $%,.2f".format(Locale.US, amount),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

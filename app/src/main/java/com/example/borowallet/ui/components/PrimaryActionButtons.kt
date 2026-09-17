package com.example.borowallet.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borowallet.ui.theme.BoroPrimaryVariant

@Composable
fun PrimaryActionButtons(
    onBuySellClick: () -> Unit,
    onTransferClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Buy & Sell (Primary Pink)
        Button(
            onClick = onBuySellClick,
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
                .testTag("buy_sell_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = BoroPrimaryVariant,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(26.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SwapVert,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Buy & Sell",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        // Transfer (Surface Variant Container)
        Button(
            onClick = onTransferClick,
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
                .testTag("transfer_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(26.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Transfer",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

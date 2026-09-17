package com.example.borowallet.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borowallet.ui.theme.BoroAccentSoft
import com.example.borowallet.ui.theme.BoroPrimaryVariant
import com.example.borowallet.ui.theme.GainGreen
import java.util.Locale

@Composable
fun PortfolioSummaryCard(
    totalValue: Double,
    isBalanceHidden: Boolean,
    isLive: Boolean,
    onToggleBalance: () -> Unit,
    onToggleLive: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        // Top Subheader row: Net Worth + Eye toggle + Live pill
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Net Worth",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                )

                Icon(
                    imageVector = if (isBalanceHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Toggle Balance Visibility",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .clickable { onToggleBalance() }
                        .testTag("toggle_balance_button")
                )
            }

            // Live status pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isLive) Color(0x1900875A) else MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onToggleLive() }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .testTag("live_ticker_toggle"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(
                            if (isLive) GainGreen.copy(alpha = pulseAlpha) else Color.Gray
                        )
                )
                Text(
                    text = if (isLive) "LIVE" else "PAUSED",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isLive) GainGreen else Color.Gray,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Large Total Balance Text
        AnimatedContent(
            targetState = isBalanceHidden,
            label = "balance_anim"
        ) { hidden ->
            if (hidden) {
                Text(
                    text = "$••••••••",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 36.sp,
                        letterSpacing = (-1).sp
                    )
                )
            } else {
                Text(
                    text = "$%,.2f".format(Locale.US, totalValue),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 36.sp,
                        letterSpacing = (-1).sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // 24h Return Badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(BoroAccentSoft)
                    .padding(horizontal = 7.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                    contentDescription = null,
                    tint = BoroPrimaryVariant,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "+3.42%",
                    color = BoroPrimaryVariant,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Text(
                text = "+$3,842.10 past 24 hours",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

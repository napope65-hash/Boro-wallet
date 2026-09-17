package com.example.borowallet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borowallet.ui.theme.BoroAccentSoft
import com.example.borowallet.ui.theme.BoroPrimaryVariant

data class NavTabItem(
    val id: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun BottomNavBar(
    activeTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        NavTabItem("home", "Explore", Icons.Default.Home),
        NavTabItem("wallet", "Wallet", Icons.Default.AccountBalanceWallet),
        NavTabItem("swap", "Swap", Icons.Default.SwapHoriz),
        NavTabItem("profile", "Profile", Icons.Default.Person)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = Color(0x26000000)
                )
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.96f))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { tab ->
                val isActive = activeTab == tab.id
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(if (isActive) BoroAccentSoft else Color.Transparent)
                        .clickable { onTabSelected(tab.id) }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("nav_tab_${tab.id}"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label,
                        tint = if (isActive) BoroPrimaryVariant else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                        color = if (isActive) BoroPrimaryVariant else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

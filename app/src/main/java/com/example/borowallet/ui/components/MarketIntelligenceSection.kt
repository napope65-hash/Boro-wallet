package com.example.borowallet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borowallet.model.NewsArticle
import com.example.borowallet.ui.theme.BoroAccentSoft
import com.example.borowallet.ui.theme.BoroPrimaryVariant

@Composable
fun MarketIntelligenceSection(
    articles: List<NewsArticle>,
    onSelectArticle: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    val displayedArticles = if (isExpanded) articles else articles.take(2)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        // Section Header
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
                    text = "Market Intelligence",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = BoroPrimaryVariant,
                    modifier = Modifier.size(18.dp)
                )
            }

            TextButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.testTag("see_all_news_button")
            ) {
                Text(
                    text = if (isExpanded) "Show less" else "See all",
                    color = BoroPrimaryVariant,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Articles List
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            displayedArticles.forEach { article ->
                val isHighlight = article.category == "Crypto"
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { onSelectArticle(article) }
                        .padding(14.dp)
                        .testTag("news_card_${article.id}"),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Category + Source + Time
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isHighlight) BoroAccentSoft else Color(0xFFDDE2F2))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = article.category,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isHighlight) BoroPrimaryVariant else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = "${article.source} • ${article.timeAgo}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                            )
                        )
                    }

                    // Title
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            lineHeight = 20.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

package com.example.borowallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.borowallet.ui.components.BottomNavBar
import com.example.borowallet.ui.components.HeaderBar
import com.example.borowallet.ui.dialogs.*
import com.example.borowallet.ui.screens.ExploreScreen
import com.example.borowallet.ui.screens.ProfileScreen
import com.example.borowallet.ui.screens.SwapScreen
import com.example.borowallet.ui.screens.WalletScreen
import com.example.borowallet.ui.theme.BoroWalletTheme
import com.example.borowallet.viewmodel.WalletViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: WalletViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BoroWalletTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                val assets by viewModel.assets.collectAsStateWithLifecycle()
                val profile by viewModel.userProfile.collectAsStateWithLifecycle()
                val notifications by viewModel.notifications.collectAsStateWithLifecycle()
                val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
                val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
                val selectedTimeframe by viewModel.selectedTimeframe.collectAsStateWithLifecycle()
                val isBalanceHidden by viewModel.isBalanceHidden.collectAsStateWithLifecycle()
                val isLiveTickerActive by viewModel.isLiveTickerActive.collectAsStateWithLifecycle()
                val sortOption by viewModel.sortOption.collectAsStateWithLifecycle()
                val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()

                // Dialog states
                val activeAssetDetail by viewModel.activeAssetDetail.collectAsStateWithLifecycle()
                val activeArticle by viewModel.activeArticle.collectAsStateWithLifecycle()
                val showBuySellDialog by viewModel.showBuySellDialog.collectAsStateWithLifecycle()
                val preselectedTradeAsset by viewModel.preselectedTradeAsset.collectAsStateWithLifecycle()
                val initialTradeTab by viewModel.initialTradeTab.collectAsStateWithLifecycle()
                val showTransferDialog by viewModel.showTransferDialog.collectAsStateWithLifecycle()
                val preselectedTransferAsset by viewModel.preselectedTransferAsset.collectAsStateWithLifecycle()
                val initialTransferTab by viewModel.initialTransferTab.collectAsStateWithLifecycle()
                val showRewardsDialog by viewModel.showRewardsDialog.collectAsStateWithLifecycle()
                val showNotificationsDrawer by viewModel.showNotificationsDrawer.collectAsStateWithLifecycle()
                val showCashDialog by viewModel.showCashDialog.collectAsStateWithLifecycle()
                val initialCashMode by viewModel.initialCashMode.collectAsStateWithLifecycle()

                // Feedback message
                val feedbackMessage by viewModel.feedbackMessage.collectAsStateWithLifecycle()
                LaunchedEffect(feedbackMessage) {
                    feedbackMessage?.let { msg ->
                        scope.launch {
                            snackbarHostState.showSnackbar(msg)
                            viewModel.clearFeedbackMessage()
                        }
                    }
                }

                // Total calculated portfolio value (all assets holdings + cash balance)
                val totalAssetsValue = assets.sumOf { it.totalValue }
                val totalPortfolioValue = totalAssetsValue + profile.cashBalance
                val unreadNotificationsCount = notifications.count { !it.isRead }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        HeaderBar(
                            unreadCount = unreadNotificationsCount,
                            onOpenNotifications = { viewModel.openNotifications() },
                            onOpenRewards = { viewModel.openRewards() },
                            onOpenProfile = { viewModel.setActiveTab("profile") }
                        )
                    },
                    bottomBar = {
                        BottomNavBar(
                            activeTab = activeTab,
                            onTabSelected = { tab -> viewModel.setActiveTab(tab) }
                        )
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    contentWindowInsets = WindowInsets.safeDrawing
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (activeTab) {
                            "wallet" -> {
                                WalletScreen(
                                    assets = assets,
                                    totalPortfolioValue = totalPortfolioValue,
                                    isBalanceHidden = isBalanceHidden,
                                    isLiveTickerActive = isLiveTickerActive,
                                    selectedCategory = selectedCategory,
                                    searchQuery = searchQuery,
                                    selectedTimeframe = selectedTimeframe,
                                    sortOption = sortOption,
                                    onToggleBalance = { viewModel.toggleBalanceHidden() },
                                    onToggleLive = { viewModel.toggleLiveTicker() },
                                    onSelectCategory = { cat -> viewModel.selectCategory(cat) },
                                    onSearchChange = { q -> viewModel.setSearchQuery(q) },
                                    onSelectTimeframe = { tf -> viewModel.setTimeframe(tf) },
                                    onSortChange = { opt -> viewModel.setSortOption(opt) },
                                    onBuySellClick = { viewModel.openBuySell() },
                                    onTransferClick = { viewModel.openTransfer() },
                                    onSelectAsset = { asset -> viewModel.openAssetDetail(asset) },
                                    onSelectArticle = { article -> viewModel.openArticle(article) },
                                    onInviteClick = { viewModel.openRewards() }
                                )
                            }
                            "home" -> {
                                ExploreScreen(
                                    assets = assets,
                                    onSelectAsset = { asset -> viewModel.openAssetDetail(asset) },
                                    onQuickTrade = { asset -> viewModel.openBuySell(asset, "buy") }
                                )
                            }
                            "swap" -> {
                                SwapScreen(
                                    assets = assets,
                                    onExecuteSwap = { fromId, toId, amount ->
                                        viewModel.executeSwap(fromId, toId, amount)
                                    }
                                )
                            }
                            "profile" -> {
                                ProfileScreen(
                                    profile = profile,
                                    onOpenCashDialog = { mode -> viewModel.openCashDialog(mode) },
                                    onOpenRewards = { viewModel.openRewards() },
                                    onToggleBiometrics = { viewModel.toggleBiometrics() }
                                )
                            }
                        }
                    }
                }

                // Modals
                if (showBuySellDialog) {
                    BuySellDialog(
                        assets = assets,
                        initialAsset = preselectedTradeAsset,
                        initialTab = initialTradeTab,
                        cashBalance = profile.cashBalance,
                        onDismiss = { viewModel.closeBuySell() },
                        onExecuteTrade = { assetId, isBuy, amountUsd ->
                            viewModel.executeTrade(assetId, isBuy, amountUsd)
                        }
                    )
                }

                if (showTransferDialog) {
                    TransferDialog(
                        assets = assets,
                        initialAsset = preselectedTransferAsset,
                        initialTab = initialTransferTab,
                        onDismiss = { viewModel.closeTransfer() },
                        onExecuteTransfer = { assetId, isSend, amountUnits, recipient ->
                            viewModel.executeTransfer(assetId, isSend, amountUnits, recipient)
                        }
                    )
                }

                activeAssetDetail?.let { asset ->
                    AssetDetailDialog(
                        asset = asset,
                        onDismiss = { viewModel.closeAssetDetail() },
                        onOpenBuySell = { a -> viewModel.openBuySell(a, "buy") },
                        onOpenTransfer = { a -> viewModel.openTransfer(a, "send") }
                    )
                }

                activeArticle?.let { article ->
                    ArticleDetailDialog(
                        article = article,
                        onDismiss = { viewModel.closeArticle() }
                    )
                }

                if (showRewardsDialog) {
                    RewardsDialog(
                        referralCode = profile.referralCode,
                        friendsInvited = profile.friendsInvited,
                        totalRewardsEarned = profile.totalRewardsEarnedBtc,
                        onDismiss = { viewModel.closeRewards() }
                    )
                }

                if (showNotificationsDrawer) {
                    NotificationsDrawer(
                        notifications = notifications,
                        onMarkAllAsRead = { viewModel.markAllNotificationsAsRead() },
                        onClearAll = { viewModel.clearAllNotifications() },
                        onDismiss = { viewModel.closeNotifications() }
                    )
                }

                if (showCashDialog) {
                    CashManagementDialog(
                        initialMode = initialCashMode,
                        currentCashBalance = profile.cashBalance,
                        onDismiss = { viewModel.closeCashDialog() },
                        onExecuteCashOperation = { isDeposit, amount ->
                            viewModel.executeCashOperation(isDeposit, amount)
                        }
                    )
                }
            }
        }
    }
}

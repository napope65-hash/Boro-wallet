package com.example.borowallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.borowallet.data.MockData
import com.example.borowallet.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class WalletViewModel : ViewModel() {

    private val _assets = MutableStateFlow<List<Asset>>(MockData.initialAssets)
    val assets: StateFlow<List<Asset>> = _assets.asStateFlow()

    private val _userProfile = MutableStateFlow<UserProfile>(MockData.initialProfile)
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _notifications = MutableStateFlow<List<NotificationItem>>(MockData.notifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _selectedCategory = MutableStateFlow(AssetCategory.ALL)
    val selectedCategory: StateFlow<AssetCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTimeframe = MutableStateFlow("1D")
    val selectedTimeframe: StateFlow<String> = _selectedTimeframe.asStateFlow()

    private val _isBalanceHidden = MutableStateFlow(false)
    val isBalanceHidden: StateFlow<Boolean> = _isBalanceHidden.asStateFlow()

    private val _isLiveTickerActive = MutableStateFlow(true)
    val isLiveTickerActive: StateFlow<Boolean> = _isLiveTickerActive.asStateFlow()

    private val _sortOption = MutableStateFlow("value-desc")
    val sortOption: StateFlow<String> = _sortOption.asStateFlow()

    private val _activeTab = MutableStateFlow("wallet")
    val activeTab: StateFlow<String> = _activeTab.asStateFlow()

    // Dialog state
    private val _activeAssetDetail = MutableStateFlow<Asset?>(null)
    val activeAssetDetail: StateFlow<Asset?> = _activeAssetDetail.asStateFlow()

    private val _activeArticle = MutableStateFlow<NewsArticle?>(null)
    val activeArticle: StateFlow<NewsArticle?> = _activeArticle.asStateFlow()

    private val _showBuySellDialog = MutableStateFlow(false)
    val showBuySellDialog: StateFlow<Boolean> = _showBuySellDialog.asStateFlow()

    private val _preselectedTradeAsset = MutableStateFlow<Asset?>(null)
    val preselectedTradeAsset: StateFlow<Asset?> = _preselectedTradeAsset.asStateFlow()

    private val _initialTradeTab = MutableStateFlow("buy")
    val initialTradeTab: StateFlow<String> = _initialTradeTab.asStateFlow()

    private val _showTransferDialog = MutableStateFlow(false)
    val showTransferDialog: StateFlow<Boolean> = _showTransferDialog.asStateFlow()

    private val _preselectedTransferAsset = MutableStateFlow<Asset?>(null)
    val preselectedTransferAsset: StateFlow<Asset?> = _preselectedTransferAsset.asStateFlow()

    private val _initialTransferTab = MutableStateFlow("send")
    val initialTransferTab: StateFlow<String> = _initialTransferTab.asStateFlow()

    private val _showRewardsDialog = MutableStateFlow(false)
    val showRewardsDialog: StateFlow<Boolean> = _showRewardsDialog.asStateFlow()

    private val _showNotificationsDrawer = MutableStateFlow(false)
    val showNotificationsDrawer: StateFlow<Boolean> = _showNotificationsDrawer.asStateFlow()

    private val _showCashDialog = MutableStateFlow(false)
    val showCashDialog: StateFlow<Boolean> = _showCashDialog.asStateFlow()

    private val _initialCashMode = MutableStateFlow("deposit")
    val initialCashMode: StateFlow<String> = _initialCashMode.asStateFlow()

    private val _feedbackMessage = MutableStateFlow<String?>(null)
    val feedbackMessage: StateFlow<String?> = _feedbackMessage.asStateFlow()

    init {
        startLivePriceTicker()
    }

    private fun startLivePriceTicker() {
        viewModelScope.launch {
            while (true) {
                delay(4000)
                if (_isLiveTickerActive.value) {
                    _assets.update { currentList ->
                        currentList.map { asset ->
                            // gentle micro fluctuation +-0.25%
                            val factor = 1.0 + (Random.nextDouble(-0.0025, 0.003))
                            val newPrice = (asset.currentPrice * factor * 100).toLong() / 100.0
                            val priceDeltaPct = ((newPrice - asset.currentPrice) / asset.currentPrice) * 100.0
                            val updatedChange = ((asset.change24h + priceDeltaPct * 0.05) * 100).toLong() / 100.0
                            asset.copy(
                                currentPrice = maxOf(0.01, newPrice),
                                change24h = updatedChange
                            )
                        }
                    }
                }
            }
        }
    }

    fun selectCategory(category: AssetCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setTimeframe(tf: String) {
        _selectedTimeframe.value = tf
    }

    fun toggleBalanceHidden() {
        _isBalanceHidden.update { !it }
    }

    fun toggleLiveTicker() {
        _isLiveTickerActive.update { !it }
    }

    fun setSortOption(option: String) {
        _sortOption.value = option
    }

    fun setActiveTab(tab: String) {
        _activeTab.value = tab
    }

    fun openAssetDetail(asset: Asset) {
        _activeAssetDetail.value = asset
    }

    fun closeAssetDetail() {
        _activeAssetDetail.value = null
    }

    fun openArticle(article: NewsArticle) {
        _activeArticle.value = article
    }

    fun closeArticle() {
        _activeArticle.value = null
    }

    fun openBuySell(asset: Asset? = null, tab: String = "buy") {
        _preselectedTradeAsset.value = asset ?: _assets.value.firstOrNull()
        _initialTradeTab.value = tab
        _showBuySellDialog.value = true
    }

    fun closeBuySell() {
        _showBuySellDialog.value = false
    }

    fun openTransfer(asset: Asset? = null, tab: String = "send") {
        _preselectedTransferAsset.value = asset ?: _assets.value.firstOrNull()
        _initialTransferTab.value = tab
        _showTransferDialog.value = true
    }

    fun closeTransfer() {
        _showTransferDialog.value = false
    }

    fun openRewards() {
        _showRewardsDialog.value = true
    }

    fun closeRewards() {
        _showRewardsDialog.value = false
    }

    fun openNotifications() {
        _showNotificationsDrawer.value = true
    }

    fun closeNotifications() {
        _showNotificationsDrawer.value = false
    }

    fun openCashDialog(mode: String = "deposit") {
        _initialCashMode.value = mode
        _showCashDialog.value = true
    }

    fun closeCashDialog() {
        _showCashDialog.value = false
    }

    fun clearFeedbackMessage() {
        _feedbackMessage.value = null
    }

    fun executeTrade(assetId: String, isBuy: Boolean, amountUsd: Double): Boolean {
        val asset = _assets.value.find { it.id == assetId } ?: return false
        if (amountUsd <= 0) return false

        if (isBuy) {
            if (_userProfile.value.cashBalance < amountUsd) {
                _feedbackMessage.value = "Insufficient USD balance"
                return false
            }
            val tokensBought = amountUsd / asset.currentPrice
            _userProfile.update { it.copy(cashBalance = it.cashBalance - amountUsd) }
            _assets.update { list ->
                list.map {
                    if (it.id == assetId) it.copy(sharesOrTokens = it.sharesOrTokens + tokensBought) else it
                }
            }
            addNotification(
                title = "Order Executed: Bought ${asset.symbol}",
                description = "Acquired %.4f %s for $%.2f at $%.2f/unit".format(tokensBought, asset.unitLabel, amountUsd, asset.currentPrice),
                type = NotificationType.TRANSACTION
            )
            _feedbackMessage.value = "Successfully purchased ${asset.symbol}!"
            return true
        } else {
            val tokensToSell = amountUsd / asset.currentPrice
            if (asset.sharesOrTokens < tokensToSell) {
                _feedbackMessage.value = "Insufficient ${asset.symbol} holdings"
                return false
            }
            _userProfile.update { it.copy(cashBalance = it.cashBalance + amountUsd) }
            _assets.update { list ->
                list.map {
                    if (it.id == assetId) it.copy(sharesOrTokens = maxOf(0.0, it.sharesOrTokens - tokensToSell)) else it
                }
            }
            addNotification(
                title = "Order Executed: Sold ${asset.symbol}",
                description = "Sold %.4f %s for $%.2f".format(tokensToSell, asset.unitLabel, amountUsd),
                type = NotificationType.TRANSACTION
            )
            _feedbackMessage.value = "Successfully sold ${asset.symbol}!"
            return true
        }
    }

    fun executeTransfer(assetId: String, isSend: Boolean, amountUnits: Double, recipient: String): Boolean {
        val asset = _assets.value.find { it.id == assetId } ?: return false
        if (amountUnits <= 0) return false

        if (isSend) {
            if (asset.sharesOrTokens < amountUnits) {
                _feedbackMessage.value = "Insufficient ${asset.symbol} balance"
                return false
            }
            _assets.update { list ->
                list.map {
                    if (it.id == assetId) it.copy(sharesOrTokens = maxOf(0.0, it.sharesOrTokens - amountUnits)) else it
                }
            }
            addNotification(
                title = "Transfer Sent: ${asset.symbol}",
                description = "Transferred %.4f %s to %s".format(amountUnits, asset.unitLabel, recipient.ifBlank { "0x89a...4b1" }),
                type = NotificationType.TRANSACTION
            )
            _feedbackMessage.value = "Transfer completed!"
            return true
        } else {
            _assets.update { list ->
                list.map {
                    if (it.id == assetId) it.copy(sharesOrTokens = it.sharesOrTokens + amountUnits) else it
                }
            }
            addNotification(
                title = "Transfer Received: ${asset.symbol}",
                description = "Deposited %.4f %s into Boro Vault".format(amountUnits, asset.unitLabel),
                type = NotificationType.TRANSACTION
            )
            _feedbackMessage.value = "Funds received into your vault!"
            return true
        }
    }

    fun executeSwap(fromAssetId: String, toAssetId: String, fromAmount: Double): Boolean {
        if (fromAssetId == toAssetId || fromAmount <= 0) return false
        val fromAsset = _assets.value.find { it.id == fromAssetId } ?: return false
        val toAsset = _assets.value.find { it.id == toAssetId } ?: return false

        if (fromAsset.sharesOrTokens < fromAmount) {
            _feedbackMessage.value = "Insufficient ${fromAsset.symbol} balance"
            return false
        }

        val totalValueUsd = fromAmount * fromAsset.currentPrice
        val toAmount = totalValueUsd / toAsset.currentPrice

        _assets.update { list ->
            list.map {
                when (it.id) {
                    fromAssetId -> it.copy(sharesOrTokens = maxOf(0.0, it.sharesOrTokens - fromAmount))
                    toAssetId -> it.copy(sharesOrTokens = it.sharesOrTokens + toAmount)
                    else -> it
                }
            }
        }

        addNotification(
            title = "Zero-Slippage Swap Completed",
            description = "Swapped %.4f %s for %.4f %s".format(fromAmount, fromAsset.symbol, toAmount, toAsset.symbol),
            type = NotificationType.TRANSACTION
        )
        _feedbackMessage.value = "Swapped ${fromAsset.symbol} for ${toAsset.symbol}!"
        return true
    }

    fun executeCashOperation(isDeposit: Boolean, amountUsd: Double): Boolean {
        if (amountUsd <= 0) return false
        if (isDeposit) {
            _userProfile.update { it.copy(cashBalance = it.cashBalance + amountUsd) }
            addNotification(
                title = "Deposit Settled",
                description = "$%.2f transferred from Chase Premier Checking".format(amountUsd),
                type = NotificationType.TRANSACTION
            )
            _feedbackMessage.value = "Deposited $%.2f into Boro Cash".format(amountUsd)
            return true
        } else {
            if (_userProfile.value.cashBalance < amountUsd) {
                _feedbackMessage.value = "Insufficient Boro Cash balance"
                return false
            }
            _userProfile.update { it.copy(cashBalance = it.cashBalance - amountUsd) }
            addNotification(
                title = "Withdrawal Initiated",
                description = "$%.2f outbound to Chase Premier Checking".format(amountUsd),
                type = NotificationType.TRANSACTION
            )
            _feedbackMessage.value = "Withdrawal of $%.2f initiated".format(amountUsd)
            return true
        }
    }

    fun markAllNotificationsAsRead() {
        _notifications.update { list ->
            list.map { it.copy(isRead = true) }
        }
    }

    fun clearAllNotifications() {
        _notifications.value = emptyList()
    }

    fun toggleBiometrics() {
        _userProfile.update { it.copy(isBiometricsEnabled = !it.isBiometricsEnabled) }
    }

    private fun addNotification(title: String, description: String, type: NotificationType) {
        val newItem = NotificationItem(
            id = "notif-${System.currentTimeMillis()}",
            title = title,
            description = description,
            timeAgo = "Just now",
            isRead = false,
            type = type
        )
        _notifications.update { listOf(newItem) + it }
    }
}

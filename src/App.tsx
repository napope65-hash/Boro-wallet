import React, { useState, useRef, useEffect } from 'react';
import { Asset, AssetCategory, NewsArticle, Timeframe, UserProfile } from './types';
import {
  INITIAL_ASSETS,
  INITIAL_NOTIFICATIONS,
  INITIAL_USER,
  NEWS_ARTICLES,
} from './data/mockData';
import { Header } from './components/Header';
import { PortfolioSummary } from './components/PortfolioSummary';
import { PortfolioChart } from './components/PortfolioChart';
import { PrimaryActions } from './components/PrimaryActions';
import { AssetList } from './components/AssetList';
import { MarketIntelligence } from './components/MarketIntelligence';
import { ReferralBanner } from './components/ReferralBanner';
import { BottomNav, NavTab } from './components/BottomNav';
import { BuySellModal } from './components/modals/BuySellModal';
import { TransferModal } from './components/modals/TransferModal';
import { AssetDetailModal } from './components/modals/AssetDetailModal';
import { ArticleModal } from './components/modals/ArticleModal';
import { RewardsModal } from './components/modals/RewardsModal';
import { NotificationsDrawer } from './components/modals/NotificationsDrawer';
import { HomeView } from './components/views/HomeView';
import { SwapView } from './components/views/SwapView';
import { ProfileView } from './components/views/ProfileView';

export default function App() {
  // Navigation & View state
  const [currentTab, setCurrentTab] = useState<NavTab>('wallet');

  // Core Data state
  const [assets, setAssets] = useState<Asset[]>(INITIAL_ASSETS);
  const [user, setUser] = useState<UserProfile>(INITIAL_USER);
  const [notifications, setNotifications] = useState(INITIAL_NOTIFICATIONS);

  // Wallet specific interactive state
  const [selectedTimeframe, setSelectedTimeframe] = useState<Timeframe>('1D');
  const [isBalanceHidden, setIsBalanceHidden] = useState<boolean>(false);
  const [isLive, setIsLive] = useState<boolean>(true);
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [activeCategory, setActiveCategory] = useState<AssetCategory>('all');
  const [isNewsExpanded, setIsNewsExpanded] = useState<boolean>(false);

  // Modals & Drawers state
  const [isBuySellOpen, setIsBuySellOpen] = useState(false);
  const [tradeInitialAsset, setTradeInitialAsset] = useState<Asset | undefined>(undefined);
  const [isTransferOpen, setIsTransferOpen] = useState(false);
  const [selectedAssetDetail, setSelectedAssetDetail] = useState<Asset | null>(null);
  const [selectedArticle, setSelectedArticle] = useState<NewsArticle | null>(null);
  const [isRewardsOpen, setIsRewardsOpen] = useState(false);
  const [isNotificationsOpen, setIsNotificationsOpen] = useState(false);

  const searchInputRef = useRef<HTMLInputElement | null>(null);

  // Simulated gentle live price updates when isLive is true
  useEffect(() => {
    if (!isLive) return;
    const interval = setInterval(() => {
      setAssets((prev) =>
        prev.map((asset) => {
          // Subtle micro fluctuation (-0.1% to +0.1%)
          const jitter = 1 + (Math.random() * 0.002 - 0.001);
          const newPrice = Math.round(asset.currentPrice * jitter * 100) / 100;
          return {
            ...asset,
            currentPrice: newPrice,
          };
        })
      );
    }, 4000);
    return () => clearInterval(interval);
  }, [isLive]);

  // Execute trade (Buy/Sell)
  const handleExecuteTrade = (
    type: 'buy' | 'sell',
    assetId: string,
    amountUSD: number,
    units: number
  ) => {
    setAssets((prev) =>
      prev.map((asset) => {
        if (asset.id !== assetId) return asset;
        const newUnits =
          type === 'buy'
            ? asset.sharesOrTokens + units
            : Math.max(0, asset.sharesOrTokens - units);
        return {
          ...asset,
          sharesOrTokens: parseFloat(newUnits.toFixed(4)),
        };
      })
    );

    // Adjust user cash balance if trade settled against cash
    setUser((prev) => ({
      ...prev,
      cashBalance:
        type === 'buy'
          ? Math.max(0, prev.cashBalance - amountUSD)
          : prev.cashBalance + amountUSD,
    }));

    // Add notification
    const assetObj = assets.find((a) => a.id === assetId);
    if (assetObj) {
      setNotifications((prev) => [
        {
          id: 'notif-' + Date.now(),
          title: `Order Completed: ${type.toUpperCase()}`,
          message: `${type === 'buy' ? 'Bought' : 'Sold'} ${units.toFixed(
            4
          )} ${assetObj.symbol} for $${amountUSD.toFixed(2)}`,
          timeAgo: 'Just now',
          read: false,
          type: 'reward',
        },
        ...prev,
      ]);
    }
  };

  // Execute Send
  const handleExecuteSend = (assetId: string, amount: number, recipient: string) => {
    setAssets((prev) =>
      prev.map((asset) => {
        if (asset.id !== assetId) return asset;
        return {
          ...asset,
          sharesOrTokens: Math.max(0, asset.sharesOrTokens - amount),
        };
      })
    );

    const assetObj = assets.find((a) => a.id === assetId);
    setNotifications((prev) => [
      {
        id: 'notif-' + Date.now(),
        title: 'Transfer Dispatched',
        message: `Sent ${amount} ${assetObj?.symbol || ''} to ${recipient}`,
        timeAgo: 'Just now',
        read: false,
        type: 'system',
      },
      ...prev,
    ]);
  };

  const handleDepositCash = (amount: number) => {
    setUser((prev) => ({ ...prev, cashBalance: prev.cashBalance + amount }));
  };

  const handleWithdrawCash = (amount: number) => {
    setUser((prev) => ({
      ...prev,
      cashBalance: Math.max(0, prev.cashBalance - amount),
    }));
  };

  const unreadNotifCount = notifications.filter((n) => !n.read).length;

  const handleMarkAllRead = () => {
    setNotifications((prev) => prev.map((n) => ({ ...n, read: true })));
  };

  const handleClearNotifications = () => {
    setNotifications([]);
  };

  const handleFocusSearch = () => {
    if (currentTab !== 'wallet') {
      setCurrentTab('wallet');
    }
    setTimeout(() => {
      searchInputRef.current?.focus();
    }, 100);
  };

  const handleOpenTradeForAsset = (asset: Asset) => {
    setTradeInitialAsset(asset);
    setIsBuySellOpen(true);
  };

  return (
    <div className="bg-[#FDFCFB] text-[#1A1C1F] flex flex-col min-h-screen relative selection:bg-[#FF005E]/20">
      {/* Top Header */}
      <Header
        unreadCount={unreadNotifCount}
        onOpenNotifications={() => setIsNotificationsOpen(true)}
        onOpenRewards={() => setIsRewardsOpen(true)}
        onOpenProfile={() => setCurrentTab('profile')}
        onFocusSearch={handleFocusSearch}
        title={
          currentTab === 'wallet'
            ? 'Wallet'
            : currentTab === 'home'
            ? 'Explore'
            : currentTab === 'swap'
            ? 'Swap'
            : 'Profile'
        }
      />

      {/* Main Content Area with safe area clearance */}
      <main className="flex-1 flex flex-col relative w-full pt-28 pb-32 bg-[#FDFCFB]">
        {currentTab === 'wallet' && (
          <div className="flex flex-col w-full max-w-lg mx-auto">
            {/* Search Bar Inset */}
            <PortfolioSummary
              searchQuery={searchQuery}
              onSearchChange={setSearchQuery}
              searchInputRef={searchInputRef}
              isBalanceHidden={isBalanceHidden}
              onToggleBalance={() => setIsBalanceHidden(!isBalanceHidden)}
              selectedTimeframe={selectedTimeframe}
              isLive={isLive}
              onToggleLive={() => setIsLive(!isLive)}
            />

            {/* Interactive Portfolio Chart */}
            <PortfolioChart
              selectedTimeframe={selectedTimeframe}
              onSelectTimeframe={setSelectedTimeframe}
              isBalanceHidden={isBalanceHidden}
            />

            {/* Primary Action Buttons */}
            <PrimaryActions
              onBuySellClick={() => {
                setTradeInitialAsset(undefined);
                setIsBuySellOpen(true);
              }}
              onTransferClick={() => setIsTransferOpen(true)}
            />

            {/* Asset Category Filter Pills & Holdings */}
            <AssetList
              assets={assets}
              activeCategory={activeCategory}
              onSelectCategory={setActiveCategory}
              searchQuery={searchQuery}
              isBalanceHidden={isBalanceHidden}
              onSelectAsset={(asset) => setSelectedAssetDetail(asset)}
            />

            {/* Market Intelligence & News Section */}
            <MarketIntelligence
              articles={isNewsExpanded ? NEWS_ARTICLES : NEWS_ARTICLES.slice(0, 3)}
              onSelectArticle={(article) => setSelectedArticle(article)}
              onSeeAll={() => setIsNewsExpanded(!isNewsExpanded)}
              isExpanded={isNewsExpanded}
            />

            {/* Referral / Reward Incentive Pill Banner */}
            <ReferralBanner onInviteClick={() => setIsRewardsOpen(true)} />

            {/* Bottom clearance for floating pill navigation */}
            <div className="h-6" />
          </div>
        )}

        {currentTab === 'home' && (
          <HomeView
            assets={assets}
            onSelectAsset={(asset) => setSelectedAssetDetail(asset)}
            onGoToWallet={() => setCurrentTab('wallet')}
            onOpenTrade={handleOpenTradeForAsset}
          />
        )}

        {currentTab === 'swap' && (
          <SwapView
            assets={assets}
            cashBalance={user.cashBalance}
            onExecuteTrade={handleExecuteTrade}
          />
        )}

        {currentTab === 'profile' && (
          <ProfileView
            user={user}
            onDepositCash={handleDepositCash}
            onWithdrawCash={handleWithdrawCash}
          />
        )}
      </main>

      {/* Floating Bottom Navigation Bar */}
      <BottomNav currentTab={currentTab} onTabChange={setCurrentTab} />

      {/* Modals and Drawers */}
      <BuySellModal
        isOpen={isBuySellOpen}
        onClose={() => setIsBuySellOpen(false)}
        assets={assets}
        initialAsset={tradeInitialAsset}
        cashBalance={user.cashBalance}
        onExecuteTrade={handleExecuteTrade}
      />

      <TransferModal
        isOpen={isTransferOpen}
        onClose={() => setIsTransferOpen(false)}
        assets={assets}
        onExecuteSend={handleExecuteSend}
      />

      <AssetDetailModal
        asset={selectedAssetDetail}
        isOpen={!!selectedAssetDetail}
        onClose={() => setSelectedAssetDetail(null)}
        onOpenTrade={(asset) => {
          setTradeInitialAsset(asset);
          setIsBuySellOpen(true);
        }}
      />

      <ArticleModal
        article={selectedArticle}
        isOpen={!!selectedArticle}
        onClose={() => setSelectedArticle(null)}
      />

      <RewardsModal
        isOpen={isRewardsOpen}
        onClose={() => setIsRewardsOpen(false)}
      />

      <NotificationsDrawer
        isOpen={isNotificationsOpen}
        onClose={() => setIsNotificationsOpen(false)}
        notifications={notifications}
        onMarkAllAsRead={handleMarkAllRead}
        onClearNotifications={handleClearNotifications}
      />
    </div>
  );
}

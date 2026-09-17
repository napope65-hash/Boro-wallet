export type AssetCategory = 'all' | 'crypto' | 'stocks' | 'etfs';

export type Timeframe = '1D' | '1W' | '1M' | '1Y' | 'ALL';

export interface Asset {
  id: string;
  name: string;
  symbol: string;
  type: 'crypto' | 'stocks' | 'etfs';
  sharesOrTokens: number;
  unitLabel: string; // e.g. "BTC", "Shares", "ETH"
  currentPrice: number;
  change24h: number; // percentage
  iconType: 'symbol' | 'material' | 'letter';
  iconValue: string;
  iconBg: string;
  iconColor: string;
  marketCap?: string;
  volume24h?: string;
  high52w?: number;
  low52w?: number;
  historicalPoints?: number[];
  about?: string;
}

export interface NewsArticle {
  id: string;
  title: string;
  category: string;
  source: string;
  timeAgo: string;
  imageUrl: string;
  imageAlt: string;
  summary: string;
  sentiment: 'Bullish' | 'Neutral' | 'Bearish';
  readTime: string;
}

export interface NotificationItem {
  id: string;
  title: string;
  message: string;
  timeAgo: string;
  read: boolean;
  type: 'price' | 'system' | 'reward';
}

export interface UserProfile {
  name: string;
  tag: string;
  email: string;
  avatarUrl?: string;
  cashBalance: number;
  verified: boolean;
  tier: string;
}

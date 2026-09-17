import { Asset, NewsArticle, NotificationItem, Timeframe, UserProfile } from '../types';

export const BRAND_LOGO_URL =
  'https://lh3.googleusercontent.com/aida/AEtjO1U6z3pNwsN3pbm8IhUYyPgiC8V_qDhv1jJ74JbnguCP86ecD0OQ0tLeAa1FTUifkNP2hUhImsJoKF1FFs04aUWOJTBC_xGt7qygsSs0H2Kj4bv9skPMHW_t6q7CLG2BDvvI9DLhVICAj1QXYUjv5VFboXmPsfqx8STdwb9QjbVqG5r35qrg2dV8S38032q0GS4Jej_4A0Su6oSLSV9SOSKmIHu-_PVPdwb-btpLuP2r0yeniZY9LcIz6w';

export const INITIAL_USER: UserProfile = {
  name: 'Alexandre Sterling',
  tag: '$alex_sterling',
  email: 'napope65@gmail.com',
  cashBalance: 4250.0,
  verified: true,
  tier: 'Boro Private Member',
};

export const INITIAL_ASSETS: Asset[] = [
  {
    id: 'btc',
    name: 'Bitcoin',
    symbol: 'BTC',
    type: 'crypto',
    sharesOrTokens: 0.42,
    unitLabel: 'BTC',
    currentPrice: 68420.0,
    change24h: 2.4,
    iconType: 'symbol',
    iconValue: '₿',
    iconBg: '#FFF2E0',
    iconColor: '#E68200',
    marketCap: '$1.34T',
    volume24h: '$34.2B',
    high52w: 73750.07,
    low52w: 26500.0,
    historicalPoints: [66200, 66800, 66500, 67100, 67800, 67400, 68420],
    about:
      'Bitcoin is the first decentralized digital currency, enabling peer-to-peer transfers across the globe without intermediaries.',
  },
  {
    id: 'aapl',
    name: 'Apple Inc.',
    symbol: 'AAPL',
    type: 'stocks',
    sharesOrTokens: 15,
    unitLabel: 'Shares',
    currentPrice: 182.5,
    change24h: 1.2,
    iconType: 'material',
    iconValue: 'phone_iphone',
    iconBg: '#dde2f2',
    iconColor: '#1A1C1F',
    marketCap: '$2.82T',
    volume24h: '$48.1B',
    high52w: 199.62,
    low52w: 164.08,
    historicalPoints: [179.8, 180.5, 181.2, 180.9, 181.8, 182.1, 182.5],
    about:
      'Apple designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories, and sells a variety of related services.',
  },
  {
    id: 'qqq',
    name: 'Invesco QQQ',
    symbol: 'QQQ',
    type: 'etfs',
    sharesOrTokens: 8,
    unitLabel: 'Shares',
    currentPrice: 440.1,
    change24h: 1.8,
    iconType: 'material',
    iconValue: 'show_chart',
    iconBg: '#d5e3ff',
    iconColor: '#015baf',
    marketCap: '$260B',
    volume24h: '$12.4B',
    high52w: 452.1,
    low52w: 350.2,
    historicalPoints: [432.0, 434.5, 436.2, 435.0, 438.4, 439.1, 440.1],
    about:
      'Invesco QQQ tracks the Nasdaq-100 Index, offering exposure to the 100 largest non-financial companies listed on Nasdaq.',
  },
  {
    id: 'eth',
    name: 'Ethereum',
    symbol: 'ETH',
    type: 'crypto',
    sharesOrTokens: 3.2,
    unitLabel: 'ETH',
    currentPrice: 3580.0,
    change24h: 3.7,
    iconType: 'symbol',
    iconValue: 'Ξ',
    iconBg: '#EDF0FD',
    iconColor: '#4B68EC',
    marketCap: '$430B',
    volume24h: '$18.6B',
    high52w: 4090.0,
    low52w: 1520.0,
    historicalPoints: [3420, 3450, 3480, 3460, 3510, 3540, 3580],
    about:
      'Ethereum is a global, decentralized platform for smart contracts and decentralized financial applications.',
  },
  {
    id: 'nvda',
    name: 'Nvidia Corp.',
    symbol: 'NVDA',
    type: 'stocks',
    sharesOrTokens: 6,
    unitLabel: 'Shares',
    currentPrice: 875.2,
    change24h: 4.5,
    iconType: 'material',
    iconValue: 'memory',
    iconBg: '#E8F8EA',
    iconColor: '#1B802E',
    marketCap: '$2.15T',
    volume24h: '$39.8B',
    high52w: 974.0,
    low52w: 410.0,
    historicalPoints: [830, 842, 850, 848, 862, 868, 875.2],
    about:
      'Nvidia pioneers GPU-accelerated computing, powering modern artificial intelligence, deep learning, gaming, and data centers.',
  },
  {
    id: 'voo',
    name: 'Vanguard 500',
    symbol: 'VOO',
    type: 'etfs',
    sharesOrTokens: 5,
    unitLabel: 'Shares',
    currentPrice: 470.8,
    change24h: 0.8,
    iconType: 'material',
    iconValue: 'donut_large',
    iconBg: '#ffd9dc',
    iconColor: '#b90042',
    marketCap: '$410B',
    volume24h: '$4.2B',
    high52w: 480.5,
    low52w: 390.1,
    historicalPoints: [466.0, 467.2, 468.0, 468.9, 469.5, 470.1, 470.8],
    about:
      'Vanguard S&P 500 ETF invests in stocks in the S&P 500 Index, representing 500 of the largest U.S. publicly traded companies.',
  },
];

export const TIMEFRAME_DATA: Record<
  Timeframe,
  {
    balance: number;
    changeAmount: number;
    changePercent: number;
    label: string;
    high: number;
    low: number;
    path: string;
    areaPath: string;
    labels: string[];
  }
> = {
  '1D': {
    balance: 48924.6,
    changeAmount: 2841.15,
    changePercent: 6.16,
    label: 'Today',
    high: 51200.0,
    low: 46100.0,
    path: 'M0,75 C45,70 70,55 105,60 C140,65 170,25 210,35 C250,45 285,10 320,18 C340,22 355,8 360,5',
    areaPath:
      'M0,75 C45,70 70,55 105,60 C140,65 170,25 210,35 C250,45 285,10 320,18 C340,22 355,8 360,5 L360,90 L0,90 Z',
    labels: ['00:00', '06:00', '12:00', '18:00', 'NOW'],
  },
  '1W': {
    balance: 48924.6,
    changeAmount: 4120.4,
    changePercent: 9.15,
    label: 'This Week',
    high: 49850.0,
    low: 44200.0,
    path: 'M0,80 C50,75 80,60 120,62 C160,64 190,40 230,42 C270,44 300,20 330,24 C345,26 355,14 360,10',
    areaPath:
      'M0,80 C50,75 80,60 120,62 C160,64 190,40 230,42 C270,44 300,20 330,24 C345,26 355,14 360,10 L360,90 L0,90 Z',
    labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Today'],
  },
  '1M': {
    balance: 48924.6,
    changeAmount: 7650.2,
    changePercent: 18.52,
    label: 'Past Month',
    high: 52400.0,
    low: 41100.0,
    path: 'M0,82 C60,80 100,50 150,55 C200,60 240,30 280,25 C310,20 340,15 360,6',
    areaPath:
      'M0,82 C60,80 100,50 150,55 C200,60 240,30 280,25 C310,20 340,15 360,6 L360,90 L0,90 Z',
    labels: ['W1', 'W2', 'W3', 'W4', 'NOW'],
  },
  '1Y': {
    balance: 48924.6,
    changeAmount: 21450.0,
    changePercent: 78.1,
    label: 'Past Year',
    high: 54100.0,
    low: 26800.0,
    path: 'M0,85 C70,78 120,65 170,50 C220,38 270,32 310,20 C335,12 350,8 360,4',
    areaPath:
      'M0,85 C70,78 120,65 170,50 C220,38 270,32 310,20 C335,12 350,8 360,4 L360,90 L0,90 Z',
    labels: ['Q1', 'Q2', 'Q3', 'Q4', 'NOW'],
  },
  'ALL': {
    balance: 48924.6,
    changeAmount: 32540.0,
    changePercent: 198.6,
    label: 'All Time',
    high: 54100.0,
    low: 15400.0,
    path: 'M0,88 C80,82 140,55 190,44 C240,34 290,22 330,14 C345,10 355,6 360,3',
    areaPath:
      'M0,88 C80,82 140,55 190,44 C240,34 290,22 330,14 C345,10 355,6 360,3 L360,90 L0,90 Z',
    labels: ['2023', '2024', '2025', '2026', 'NOW'],
  },
};

export const NEWS_ARTICLES: NewsArticle[] = [
  {
    id: 'news-1',
    title: 'Tech rallies as semiconductor demand surges ahead of earnings week',
    category: 'Nasdaq • Tech',
    source: 'Bloomberg',
    timeAgo: '25m ago',
    imageUrl:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuDtcGBLszEGsg7_xmlyWSn0w0ebfS62KBh13RgOVQISdC4GCBHYiCmcAAil4lZFsofIdYXRoX0WIFViHJu4JLQCYJIf9OppD_HgkWxULZRLzRCEQhvd-nxy86LKDWAIuqKfNi-jMMvzX-9D4GygjoivTT6IP0ZHrELdrH382qBjJaeB7lHKCenYRIQJizSyA5y_fjzZ6tx7h-_Av9YDgcEBmCN3ypDJAUvbKafSDzz9GnvPmTgqyKg4',
    imageAlt:
      'Futuristic glowing microchip processor on circuit board with neon hot pink and cobalt ambient lighting, cinematic macro photography',
    summary:
      'Leading semiconductor companies experienced a significant price surge on Wednesday following robust demand projections for AI inference silicon. Institutional order flow indicates strong positioning ahead of upcoming quarterly reports.',
    sentiment: 'Bullish',
    readTime: '2 min read',
  },
  {
    id: 'news-2',
    title: 'Institutional ETF inflows push Bitcoin past key resistance levels',
    category: 'Crypto • Macro',
    source: 'Reuters',
    timeAgo: '1h ago',
    imageUrl:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuDH3I95N8xRX0xnQvGySfIET8bLN9RR4Qvqc8Rw0gQujXnJXj1vNihma57anw2OJ195sAIeEORc-TKz9eT9GfAGziTZlVbLhV_kFbDqNKCaTLqE30y7hcqS9NvxRrxPqTnXIL4mOehw0l1ILR56yDIiR4yjFg2qrBL-bVU_3OfXH5JdgUdW7aPt38uFcKi_zVGWZJ0TJuZl__ChVo1ZLeWRzny6mOiG9cgqJwk0-aVh41eQ7bjFsqz_',
    imageAlt:
      'Modern financial trading desk display with cryptographic upward candlestick charts and soft warm morning ambient light, ultra-clean aesthetic',
    summary:
      'Net spot Bitcoin ETF purchases topped $650 million across major asset managers in the past 24 hours. Analysts point to sustained liquidity depth and corporate treasury allocations as primary drivers for the breakout.',
    sentiment: 'Bullish',
    readTime: '3 min read',
  },
  {
    id: 'news-3',
    title: 'Federal Reserve signals steady rate stance bolstering equities market',
    category: 'ETF • S&P 500',
    source: 'WSJ',
    timeAgo: '3h ago',
    imageUrl:
      'https://lh3.googleusercontent.com/aida-public/AB6AXuDn_vEjq0PpfPhHloix7ekeRMApOGraocKvBHqTSh17WMz5kicQ3knXIcBIiCEmdh3LIwnSjtdc6h70QtZBQfW-MbU_xC2llZJmwu7ARPhB1FdAK58yRngViTDZhshgEkHgwHogGXra7W9lLdqR2UiZkNNahkbBJ8iuAOU8_8ktC7dol_WwMc1vY-7DDToVU12_u0DVLk8chA1pxn5s-DJ0DCASCiAV98hFCVYKKCubub7IFCcIB5DM',
    imageAlt:
      'Architectural neoclassical stone columns of the Federal Reserve building bathed in crisp sunny daylight with modern financial skyline in distance',
    summary:
      'Central bank officials reiterated their commitment to data-driven rate stabilization, giving broad equity indices room to consolidate gains without immediate tightening headwinds.',
    sentiment: 'Neutral',
    readTime: '4 min read',
  },
];

export const INITIAL_NOTIFICATIONS: NotificationItem[] = [
  {
    id: 'notif-1',
    title: 'Bitcoin Breakout Alert',
    message: 'BTC rose +2.4% past $68,400.00 in the last 4 hours.',
    timeAgo: '12m ago',
    read: false,
    type: 'price',
  },
  {
    id: 'notif-2',
    title: 'Dividend Received: AAPL',
    message: '+$14.25 quarterly cash dividend deposited to your Boro Cash balance.',
    timeAgo: '2h ago',
    read: false,
    type: 'reward',
  },
  {
    id: 'notif-3',
    title: 'Security Verified',
    message: 'Biometric passkey and withdrawal protection successfully active.',
    timeAgo: '1d ago',
    read: true,
    type: 'system',
  },
];

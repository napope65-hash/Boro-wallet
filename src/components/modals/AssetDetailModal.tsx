import React, { useState } from 'react';
import { Asset } from '../../types';

interface AssetDetailModalProps {
  asset: Asset | null;
  isOpen: boolean;
  onClose: () => void;
  onOpenTrade: (asset: Asset, initialMode?: 'buy' | 'sell') => void;
}

export const AssetDetailModal: React.FC<AssetDetailModalProps> = ({
  asset,
  isOpen,
  onClose,
  onOpenTrade,
}) => {
  if (!isOpen || !asset) return null;

  const [timeRange, setTimeRange] = useState<'1D' | '1W' | '1M' | '1Y'>('1D');
  const holdingValue = asset.currentPrice * asset.sharesOrTokens;

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/50 backdrop-blur-sm animate-in fade-in">
      <div className="w-full sm:max-w-md bg-white rounded-t-3xl sm:rounded-3xl shadow-2xl flex flex-col max-h-[90vh] overflow-hidden">
        {/* Header */}
        <div className="flex items-center justify-between px-6 pt-5 pb-3 border-b border-gray-100">
          <div className="flex items-center gap-3">
            <div
              style={{ backgroundColor: asset.iconBg }}
              className="w-10 h-10 rounded-full flex items-center justify-center shadow-xs"
            >
              {asset.iconType === 'symbol' ? (
                <span
                  style={{ color: asset.iconColor }}
                  className="text-[18px] font-bold"
                >
                  {asset.iconValue}
                </span>
              ) : (
                <span
                  style={{ color: asset.iconColor }}
                  className="material-symbols-outlined text-[20px]"
                >
                  {asset.iconValue}
                </span>
              )}
            </div>
            <div>
              <div className="flex items-center gap-1.5">
                <h3 className="text-[18px] font-bold text-[#1A1C1F]">{asset.name}</h3>
                <span className="text-[12px] font-semibold px-1.5 py-0.5 rounded bg-[#F4F5F6] text-[#404754]">
                  {asset.symbol}
                </span>
              </div>
              <span className="text-[12px] text-[#404754] uppercase tracking-wider font-semibold">
                {asset.type}
              </span>
            </div>
          </div>

          <button
            onClick={onClose}
            className="w-8 h-8 rounded-full bg-[#F4F5F6] hover:bg-[#EDEDF2] flex items-center justify-center text-[#404754] transition-colors"
          >
            <span className="material-symbols-outlined text-[18px]">close</span>
          </button>
        </div>

        {/* Content */}
        <div className="p-6 overflow-y-auto space-y-5">
          {/* Price & Trend */}
          <div>
            <div className="text-[32px] font-bold text-[#1A1C1F] tracking-tight">
              ${asset.currentPrice.toLocaleString('en-US', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2,
              })}
            </div>
            <div className="flex items-center gap-2 mt-1">
              <span className="inline-flex items-center gap-0.5 text-[13px] font-semibold text-[#e70054] bg-[rgba(255,0,94,0.10)] px-2 py-0.5 rounded-full">
                <span className="material-symbols-outlined text-[14px]">
                  arrow_drop_up
                </span>
                +{asset.change24h}% (24h)
              </span>
              <span className="text-[12px] text-[#404754]">Past 24 Hours</span>
            </div>
          </div>

          {/* Timeframe Pills for Asset */}
          <div className="flex items-center justify-between bg-[#F4F5F6] p-1 rounded-full">
            {(['1D', '1W', '1M', '1Y'] as const).map((tr) => (
              <button
                key={tr}
                onClick={() => setTimeRange(tr)}
                className={`flex-1 py-1 rounded-full text-[13px] transition-all font-medium ${
                  timeRange === tr
                    ? 'bg-white shadow-xs text-[#e70054] font-semibold'
                    : 'text-[#404754]'
                }`}
              >
                {tr}
              </button>
            ))}
          </div>

          {/* Mini Interactive Chart */}
          <div className="h-28 w-full bg-gradient-to-b from-[#F4F5F6] to-transparent rounded-2xl p-2.5 relative overflow-hidden">
            <svg className="w-full h-full" viewBox="0 0 300 70" preserveAspectRatio="none">
              <defs>
                <linearGradient id="assetGrad" x1="0" x2="0" y1="0" y2="1">
                  <stop offset="0%" stopColor="#FF005E" stopOpacity="0.3" />
                  <stop offset="100%" stopColor="#FF005E" stopOpacity="0" />
                </linearGradient>
              </defs>
              <path
                d="M0,50 Q40,40 80,45 T160,25 T240,30 T300,10 L300,70 L0,70 Z"
                fill="url(#assetGrad)"
              />
              <path
                d="M0,50 Q40,40 80,45 T160,25 T240,30 T300,10"
                fill="none"
                stroke="#FF005E"
                strokeWidth="2.5"
                strokeLinecap="round"
              />
              <circle cx="300" cy="10" r="4" fill="#FF005E" />
            </svg>
          </div>

          {/* Your Holdings Card */}
          <div className="p-4 rounded-2xl bg-[#F4F5F6] space-y-2">
            <div className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider">
              Your Position
            </div>
            <div className="flex items-center justify-between">
              <div>
                <div className="text-[20px] font-bold text-[#1A1C1F]">
                  ${holdingValue.toLocaleString('en-US', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2,
                  })}
                </div>
                <div className="text-[13px] text-[#404754]">
                  {asset.sharesOrTokens} {asset.unitLabel}
                </div>
              </div>
              <div className="text-right">
                <span className="text-[12px] font-semibold text-green-700 bg-green-100 px-2 py-0.5 rounded-full">
                  Profitable
                </span>
                <div className="text-[11px] text-[#404754] mt-1">
                  Avg Buy: ${(asset.currentPrice * 0.94).toFixed(2)}
                </div>
              </div>
            </div>
          </div>

          {/* Key Stats */}
          <div>
            <div className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider mb-2">
              Market Statistics
            </div>
            <div className="grid grid-cols-2 gap-2 text-[13px]">
              <div className="p-3 rounded-xl bg-[#F4F5F6]">
                <div className="text-[#404754] text-[11px]">Market Cap</div>
                <div className="font-semibold text-[#1A1C1F] mt-0.5">{asset.marketCap || '$1.2T'}</div>
              </div>
              <div className="p-3 rounded-xl bg-[#F4F5F6]">
                <div className="text-[#404754] text-[11px]">24h Volume</div>
                <div className="font-semibold text-[#1A1C1F] mt-0.5">{asset.volume24h || '$24.5B'}</div>
              </div>
              <div className="p-3 rounded-xl bg-[#F4F5F6]">
                <div className="text-[#404754] text-[11px]">52-Week High</div>
                <div className="font-semibold text-[#1A1C1F] mt-0.5">
                  ${asset.high52w?.toLocaleString() || (asset.currentPrice * 1.15).toFixed(2)}
                </div>
              </div>
              <div className="p-3 rounded-xl bg-[#F4F5F6]">
                <div className="text-[#404754] text-[11px]">52-Week Low</div>
                <div className="font-semibold text-[#1A1C1F] mt-0.5">
                  ${asset.low52w?.toLocaleString() || (asset.currentPrice * 0.75).toFixed(2)}
                </div>
              </div>
            </div>
          </div>

          {/* About Asset */}
          {asset.about && (
            <div>
              <div className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider mb-1">
                About {asset.name}
              </div>
              <p className="text-[13px] text-[#404754] leading-relaxed">
                {asset.about}
              </p>
            </div>
          )}

          {/* Action CTAs */}
          <div className="grid grid-cols-2 gap-3 pt-2">
            <button
              onClick={() => {
                onClose();
                onOpenTrade(asset, 'buy');
              }}
              className="py-3 rounded-full bg-[#e70054] hover:bg-[#b90042] text-white text-[15px] font-semibold shadow-md active:scale-98 transition-all cursor-pointer text-center"
            >
              Buy {asset.symbol}
            </button>
            <button
              onClick={() => {
                onClose();
                onOpenTrade(asset, 'sell');
              }}
              className="py-3 rounded-full bg-[#F4F5F6] hover:bg-[#EDEDF2] text-[#1A1C1F] text-[15px] font-semibold active:scale-98 transition-all cursor-pointer text-center"
            >
              Sell
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

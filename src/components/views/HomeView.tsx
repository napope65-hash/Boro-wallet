import React from 'react';
import { Asset } from '../../types';

interface HomeViewProps {
  assets: Asset[];
  onSelectAsset: (asset: Asset) => void;
  onGoToWallet: () => void;
  onOpenTrade: (asset: Asset) => void;
}

export const HomeView: React.FC<HomeViewProps> = ({
  assets,
  onSelectAsset,
  onGoToWallet,
  onOpenTrade,
}) => {
  const topMover = [...assets].sort((a, b) => b.change24h - a.change24h)[0];

  return (
    <div className="flex flex-col px-6 pt-2 pb-28 max-w-lg mx-auto w-full space-y-6">
      {/* Welcome Banner */}
      <div className="p-5 rounded-3xl bg-gradient-to-br from-[#F4F5F6] to-white border border-[#EDEDF2]/60 flex items-center justify-between">
        <div>
          <div className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider">
            Market Pulse
          </div>
          <h2 className="text-[22px] font-bold text-[#1A1C1F] mt-0.5">Markets are Bullish</h2>
          <p className="text-[13px] text-[#404754] mt-1">
            Global indices gained +1.8% today led by semiconductor demand.
          </p>
          <button
            onClick={onGoToWallet}
            className="mt-3 px-4 py-2 rounded-full bg-[#1A1C1F] text-white text-[13px] font-semibold hover:bg-black transition-colors"
          >
            View My Portfolio
          </button>
        </div>
        <div className="text-center p-3 bg-white rounded-2xl shadow-xs border border-gray-100 flex flex-col items-center">
          <span className="text-[28px] font-bold text-[#e70054]">74</span>
          <span className="text-[10px] font-bold text-green-700 uppercase tracking-wider">
            Greed
          </span>
          <span className="text-[9px] text-[#404754]">Index</span>
        </div>
      </div>

      {/* Market Indicators Bar */}
      <div>
        <h3 className="text-[16px] font-bold text-[#1A1C1F] mb-2.5">Global Benchmarks</h3>
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-2 text-[12px]">
          <div className="p-3 bg-[#F4F5F6] rounded-2xl">
            <span className="text-[#404754] block">S&amp;P 500</span>
            <span className="text-[14px] font-bold text-[#1A1C1F] block mt-0.5">5,117.09</span>
            <span className="text-[11px] font-semibold text-green-600">+0.82%</span>
          </div>
          <div className="p-3 bg-[#F4F5F6] rounded-2xl">
            <span className="text-[#404754] block">Nasdaq 100</span>
            <span className="text-[14px] font-bold text-[#1A1C1F] block mt-0.5">18,302.91</span>
            <span className="text-[11px] font-semibold text-green-600">+1.24%</span>
          </div>
          <div className="p-3 bg-[#F4F5F6] rounded-2xl">
            <span className="text-[#404754] block">Bitcoin</span>
            <span className="text-[14px] font-bold text-[#1A1C1F] block mt-0.5">$68,420</span>
            <span className="text-[11px] font-semibold text-green-600">+2.40%</span>
          </div>
          <div className="p-3 bg-[#F4F5F6] rounded-2xl">
            <span className="text-[#404754] block">Gold (Oz)</span>
            <span className="text-[14px] font-bold text-[#1A1C1F] block mt-0.5">$2,382.40</span>
            <span className="text-[11px] font-semibold text-green-600">+0.35%</span>
          </div>
        </div>
      </div>

      {/* Top Asset Highlight */}
      {topMover && (
        <div className="p-4 rounded-2xl bg-[#F4F5F6] flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div
              style={{ backgroundColor: topMover.iconBg }}
              className="w-10 h-10 rounded-full flex items-center justify-center font-bold text-[16px]"
            >
              {topMover.iconType === 'symbol' ? topMover.iconValue : topMover.symbol.slice(0, 2)}
            </div>
            <div>
              <div className="text-[12px] font-semibold text-[#e70054] uppercase tracking-wider">
                Top Gainer Today
              </div>
              <div className="text-[16px] font-bold text-[#1A1C1F]">
                {topMover.name} ({topMover.symbol})
              </div>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <span className="text-[15px] font-bold text-green-600">
              +{topMover.change24h}%
            </span>
            <button
              onClick={() => onOpenTrade(topMover)}
              className="px-3 py-1 rounded-full bg-[#e70054] text-white text-[12px] font-semibold hover:bg-[#b90042]"
            >
              Trade
            </button>
          </div>
        </div>
      )}

      {/* Watchlist */}
      <div>
        <div className="flex items-center justify-between mb-2">
          <h3 className="text-[16px] font-bold text-[#1A1C1F]">Trending Assets</h3>
          <span className="text-[12px] text-[#404754]">Real-time quotes</span>
        </div>
        <div className="divide-y divide-gray-100 bg-[#F4F5F6] rounded-2xl overflow-hidden">
          {assets.map((asset) => (
            <div
              key={asset.id}
              onClick={() => onSelectAsset(asset)}
              className="flex items-center justify-between p-3.5 hover:bg-[#EDEDF2] transition-colors cursor-pointer"
            >
              <div className="flex items-center gap-3">
                <div
                  style={{ backgroundColor: asset.iconBg, color: asset.iconColor }}
                  className="w-9 h-9 rounded-full flex items-center justify-center font-bold text-[14px]"
                >
                  {asset.symbol.slice(0, 3)}
                </div>
                <div>
                  <div className="text-[14px] font-semibold text-[#1A1C1F]">{asset.name}</div>
                  <div className="text-[12px] text-[#404754]">{asset.symbol}</div>
                </div>
              </div>

              <div className="text-right">
                <div className="text-[14px] font-bold text-[#1A1C1F]">
                  ${asset.currentPrice.toLocaleString('en-US', { minimumFractionDigits: 2 })}
                </div>
                <div className="text-[12px] font-semibold text-green-600">
                  +{asset.change24h}%
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

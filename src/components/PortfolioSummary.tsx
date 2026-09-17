import React from 'react';
import { Timeframe } from '../types';
import { TIMEFRAME_DATA } from '../data/mockData';

interface PortfolioSummaryProps {
  searchQuery: string;
  onSearchChange: (val: string) => void;
  searchInputRef: React.RefObject<HTMLInputElement | null>;
  isBalanceHidden: boolean;
  onToggleBalance: () => void;
  selectedTimeframe: Timeframe;
  isLive: boolean;
  onToggleLive: () => void;
}

export const PortfolioSummary: React.FC<PortfolioSummaryProps> = ({
  searchQuery,
  onSearchChange,
  searchInputRef,
  isBalanceHidden,
  onToggleBalance,
  selectedTimeframe,
  isLive,
  onToggleLive,
}) => {
  const tf = TIMEFRAME_DATA[selectedTimeframe];

  return (
    <div className="flex flex-col w-full">
      {/* Search Bar Inset */}
      <div className="px-6 pt-1 pb-2">
        <div className="flex items-center gap-2 bg-[#F4F5F6] px-4 h-11 rounded-full shadow-xs border border-transparent focus-within:border-[#1A1C1F]/20 focus-within:bg-white transition-all">
          <span className="material-symbols-outlined text-[#404754] text-[20px]">search</span>
          <input
            ref={searchInputRef}
            value={searchQuery}
            onChange={(e) => onSearchChange(e.target.value)}
            className="bg-transparent text-[15px] font-medium text-[#1A1C1F] placeholder:text-[#404754]/60 focus:outline-none w-full"
            placeholder="Search assets, stocks, crypto"
            type="text"
          />
          {searchQuery ? (
            <button
              onClick={() => onSearchChange('')}
              className="text-[#404754]/80 hover:text-[#1A1C1F] p-0.5 rounded-full"
            >
              <span className="material-symbols-outlined text-[18px]">close</span>
            </button>
          ) : (
            <span className="material-symbols-outlined text-[#404754]/70 text-[18px]">tune</span>
          )}
        </div>
      </div>

      {/* Portfolio Balance Section */}
      <section className="px-6 pt-2">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <span className="text-[14px] font-medium text-[#404754]">Portfolio Net Worth</span>
            <button
              id="toggleBalanceBtn"
              aria-label="Toggle balance visibility"
              onClick={onToggleBalance}
              className="flex items-center text-[#404754] hover:text-[#1A1C1F] transition-colors p-1"
            >
              <span className="material-symbols-outlined text-[18px]" id="eyeIcon">
                {isBalanceHidden ? 'visibility_off' : 'visibility'}
              </span>
            </button>
          </div>
          <button
            onClick={onToggleLive}
            title="Toggle Live updates simulation"
            className="flex items-center gap-1.5 bg-[#F4F5F6] hover:bg-[#EDEDF2] px-2.5 py-1 rounded-full transition-colors active:scale-95"
          >
            <span
              className={`w-2 h-2 rounded-full ${
                isLive ? 'bg-[#FF005E] animate-pulse' : 'bg-gray-400'
              }`}
            />
            <span className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider">
              {isLive ? 'Live' : 'Paused'}
            </span>
          </button>
        </div>

        {/* Main Value Display */}
        <div className="mt-1 flex items-baseline gap-2">
          <span
            id="balanceValue"
            className="text-[48px] leading-[52px] text-[#1A1C1F] font-semibold tracking-tight transition-all"
          >
            {isBalanceHidden
              ? '••••••••'
              : `$${tf.balance.toLocaleString('en-US', {
                  minimumFractionDigits: 2,
                  maximumFractionDigits: 2,
                })}`}
          </span>
        </div>

        {/* Return Metric Badge */}
        <div className="mt-2 flex items-center gap-2">
          <div className="inline-flex items-center gap-1 px-2.5 py-1 rounded-full bg-[rgba(255,0,94,0.10)] text-[#e70054]">
            <span className="material-symbols-outlined text-[16px]">trending_up</span>
            <span className="text-[12px] font-semibold tracking-wide">
              {isBalanceHidden
                ? '+•••.•• (+••.••%)'
                : `+$${tf.changeAmount.toLocaleString('en-US', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2,
                  })} (+${tf.changePercent.toFixed(2)}%) ${tf.label}`}
            </span>
          </div>
        </div>
      </section>
    </div>
  );
};

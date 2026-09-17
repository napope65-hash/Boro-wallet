import React, { useState } from 'react';
import { Asset, AssetCategory } from '../types';

interface AssetListProps {
  assets: Asset[];
  activeCategory: AssetCategory;
  onSelectCategory: (cat: AssetCategory) => void;
  searchQuery: string;
  isBalanceHidden: boolean;
  onSelectAsset: (asset: Asset) => void;
}

type SortOption = 'value-desc' | 'value-asc' | 'gain-desc' | 'name-asc';

export const AssetList: React.FC<AssetListProps> = ({
  assets,
  activeCategory,
  onSelectCategory,
  searchQuery,
  isBalanceHidden,
  onSelectAsset,
}) => {
  const [sortOption, setSortOption] = useState<SortOption>('value-desc');
  const [showSortMenu, setShowSortMenu] = useState(false);

  // Filter by category and search
  const filtered = assets.filter((asset) => {
    const matchCategory =
      activeCategory === 'all' || asset.type === activeCategory;
    const q = searchQuery.toLowerCase().trim();
    const matchSearch =
      !q ||
      asset.name.toLowerCase().includes(q) ||
      asset.symbol.toLowerCase().includes(q);
    return matchCategory && matchSearch;
  });

  // Sort
  const sorted = [...filtered].sort((a, b) => {
    const valA = a.currentPrice * a.sharesOrTokens;
    const valB = b.currentPrice * b.sharesOrTokens;
    switch (sortOption) {
      case 'value-desc':
        return valB - valA;
      case 'value-asc':
        return valA - valB;
      case 'gain-desc':
        return b.change24h - a.change24h;
      case 'name-asc':
        return a.name.localeCompare(b.name);
      default:
        return 0;
    }
  });

  const categories: { id: AssetCategory; label: string }[] = [
    { id: 'all', label: 'All Assets' },
    { id: 'crypto', label: 'Crypto' },
    { id: 'stocks', label: 'Stocks' },
    { id: 'etfs', label: 'ETFs & Nasdaq' },
  ];

  return (
    <div className="mt-6 flex flex-col">
      {/* Category Filter Pills */}
      <div className="flex items-center gap-2 overflow-x-auto px-6 no-scrollbar py-1">
        {categories.map((cat) => {
          const isActive = activeCategory === cat.id;
          return (
            <button
              key={cat.id}
              onClick={() => onSelectCategory(cat.id)}
              className={`category-pill flex-shrink-0 px-4 py-2 rounded-full text-[14px] transition-all cursor-pointer ${
                isActive
                  ? 'font-semibold bg-[#1A1C1F] text-[#FDFCFB] shadow-xs'
                  : 'text-[#404754] bg-[#F4F5F6] hover:bg-[#EDEDF2]'
              }`}
            >
              {cat.label}
            </button>
          );
        })}
      </div>

      {/* Segmented Holdings Section */}
      <section className="mt-4 px-6">
        <div className="flex items-center justify-between mb-2">
          <div className="flex items-center gap-2">
            <span className="text-[20px] font-semibold text-[#1A1C1F]">Holdings</span>
            <span className="bg-[#F4F5F6] text-[#404754] text-[12px] font-semibold px-2.5 py-0.5 rounded-full">
              {sorted.length}
            </span>
          </div>

          <div className="relative">
            <button
              onClick={() => setShowSortMenu(!showSortMenu)}
              className="flex items-center gap-0.5 text-[14px] text-[#e70054] font-medium hover:opacity-80 transition-opacity p-1 cursor-pointer"
            >
              <span>Sort</span>
              <span className="material-symbols-outlined text-[16px]">unfold_more</span>
            </button>

            {/* Sort Popover */}
            {showSortMenu && (
              <>
                <div
                  className="fixed inset-0 z-20"
                  onClick={() => setShowSortMenu(false)}
                />
                <div className="absolute right-0 top-full mt-1 w-48 bg-white rounded-2xl shadow-xl border border-black/5 py-2 z-30 animate-in fade-in zoom-in-95">
                  <div className="px-3 py-1 text-[11px] font-semibold text-gray-400 uppercase tracking-wider">
                    Sort Holdings By
                  </div>
                  <button
                    onClick={() => {
                      setSortOption('value-desc');
                      setShowSortMenu(false);
                    }}
                    className={`w-full text-left px-3 py-2 text-[13px] flex items-center justify-between hover:bg-[#F4F5F6] ${
                      sortOption === 'value-desc'
                        ? 'font-semibold text-[#e70054]'
                        : 'text-[#1A1C1F]'
                    }`}
                  >
                    <span>Total Value (High)</span>
                    {sortOption === 'value-desc' && (
                      <span className="material-symbols-outlined text-[16px]">check</span>
                    )}
                  </button>
                  <button
                    onClick={() => {
                      setSortOption('gain-desc');
                      setShowSortMenu(false);
                    }}
                    className={`w-full text-left px-3 py-2 text-[13px] flex items-center justify-between hover:bg-[#F4F5F6] ${
                      sortOption === 'gain-desc'
                        ? 'font-semibold text-[#e70054]'
                        : 'text-[#1A1C1F]'
                    }`}
                  >
                    <span>24h Gainers</span>
                    {sortOption === 'gain-desc' && (
                      <span className="material-symbols-outlined text-[16px]">check</span>
                    )}
                  </button>
                  <button
                    onClick={() => {
                      setSortOption('name-asc');
                      setShowSortMenu(false);
                    }}
                    className={`w-full text-left px-3 py-2 text-[13px] flex items-center justify-between hover:bg-[#F4F5F6] ${
                      sortOption === 'name-asc'
                        ? 'font-semibold text-[#e70054]'
                        : 'text-[#1A1C1F]'
                    }`}
                  >
                    <span>Alphabetical (A-Z)</span>
                    {sortOption === 'name-asc' && (
                      <span className="material-symbols-outlined text-[16px]">check</span>
                    )}
                  </button>
                  <button
                    onClick={() => {
                      setSortOption('value-asc');
                      setShowSortMenu(false);
                    }}
                    className={`w-full text-left px-3 py-2 text-[13px] flex items-center justify-between hover:bg-[#F4F5F6] ${
                      sortOption === 'value-asc'
                        ? 'font-semibold text-[#e70054]'
                        : 'text-[#1A1C1F]'
                    }`}
                  >
                    <span>Total Value (Low)</span>
                    {sortOption === 'value-asc' && (
                      <span className="material-symbols-outlined text-[16px]">check</span>
                    )}
                  </button>
                </div>
              </>
            )}
          </div>
        </div>

        {/* Asset Holdings List */}
        <div className="flex flex-col gap-1.5" id="assetHoldingsList">
          {sorted.length === 0 ? (
            <div className="p-8 text-center text-[#404754] bg-[#F4F5F6] rounded-2xl">
              <span className="material-symbols-outlined text-[32px] text-gray-400 mb-2">
                search_off
              </span>
              <p className="text-[14px]">No assets found for "{searchQuery}"</p>
            </div>
          ) : (
            sorted.map((asset) => {
              const totalHoldingValue = asset.currentPrice * asset.sharesOrTokens;
              return (
                <div
                  key={asset.id}
                  onClick={() => onSelectAsset(asset)}
                  className="asset-card flex items-center justify-between h-[67px] px-3.5 rounded-2xl bg-[#F4F5F6] hover:bg-[#EDEDF2] active:scale-[0.99] transition-all cursor-pointer group"
                  data-type={asset.type}
                >
                  <div className="flex items-center gap-3 min-w-0">
                    <div
                      style={{ backgroundColor: asset.iconBg }}
                      className="w-11 h-11 rounded-full flex items-center justify-center flex-shrink-0 shadow-xs"
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
                    <div className="flex flex-col min-w-0">
                      <div className="flex items-center gap-1.5">
                        <span className="text-[16px] font-semibold text-[#1A1C1F] truncate group-hover:text-[#b90042] transition-colors">
                          {asset.name}
                        </span>
                        <span className="text-[12px] font-semibold px-1.5 py-0.5 rounded bg-[#FDFCFB] text-[#404754]">
                          {asset.symbol}
                        </span>
                      </div>
                      <span className="text-[14px] text-[#404754]/80 truncate">
                        {asset.sharesOrTokens} {asset.unitLabel}
                      </span>
                    </div>
                  </div>

                  <div className="flex flex-col items-end flex-shrink-0 ml-2">
                    <span className="text-[16px] font-semibold text-[#1A1C1F] tracking-tight">
                      {isBalanceHidden
                        ? '$••••••'
                        : `$${asset.currentPrice.toLocaleString('en-US', {
                            minimumFractionDigits: 2,
                            maximumFractionDigits: 2,
                          })}`}
                    </span>
                    <span className="text-[12px] font-semibold text-[#e70054] flex items-center gap-0.5">
                      <span className="material-symbols-outlined text-[14px]">
                        arrow_drop_up
                      </span>
                      +{asset.change24h}%
                    </span>
                  </div>
                </div>
              );
            })
          )}
        </div>
      </section>
    </div>
  );
};

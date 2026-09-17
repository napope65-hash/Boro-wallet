import React from 'react';
import { BRAND_LOGO_URL } from '../data/mockData';

interface HeaderProps {
  unreadCount: number;
  onOpenNotifications: () => void;
  onOpenRewards: () => void;
  onOpenProfile: () => void;
  onFocusSearch: () => void;
  title?: string;
}

export const Header: React.FC<HeaderProps> = ({
  unreadCount,
  onOpenNotifications,
  onOpenRewards,
  onOpenProfile,
  onFocusSearch,
  title = 'Wallet',
}) => {
  return (
    <header className="fixed top-0 inset-x-0 z-40 bg-[#FDFCFB]/85 backdrop-blur-xl shadow-[0_1px_8px_rgba(0,0,0,0.03)] pt-safe">
      {/* Phone Status Bar */}
      <div className="h-11 px-6 flex items-center justify-between text-[#1A1C1F] text-[14px] font-semibold tracking-tight">
        <span className="w-12 text-left font-medium">9:41</span>
        <div className="flex items-center gap-1.5 text-[#1A1C1F]">
          <span className="material-symbols-outlined text-[16px]">signal_cellular_4_bar</span>
          <span className="material-symbols-outlined text-[16px]">wifi</span>
          <span className="material-symbols-outlined text-[18px]">battery_full</span>
        </div>
      </div>

      {/* Main App Bar */}
      <div className="h-16 px-6 flex items-center justify-between gap-4">
        {/* Left: Brand Logo & Title */}
        <div className="flex items-center gap-2.5 min-w-0 flex-1">
          <img
            alt="Boro Brand logo"
            className="h-8 w-auto object-contain flex-shrink-0"
            src={BRAND_LOGO_URL}
          />
          <div className="flex flex-col min-w-0">
            <span className="text-[20px] leading-[25px] font-semibold text-[#1A1C1F] truncate tracking-tight">
              {title}
            </span>
          </div>
        </div>

        {/* Right: Quick Actions */}
        <div className="flex items-center gap-1 flex-shrink-0">
          <button
            aria-label="Search"
            onClick={onFocusSearch}
            className="w-10 h-10 rounded-full flex items-center justify-center text-[#404754] hover:text-[#1A1C1F] hover:bg-[#EDEDF2]/60 active:scale-95 transition-all"
          >
            <span className="material-symbols-outlined text-[22px]">search</span>
          </button>

          <button
            aria-label="Rewards"
            onClick={onOpenRewards}
            className="w-10 h-10 rounded-full flex items-center justify-center text-[#404754] hover:text-[#1A1C1F] hover:bg-[#EDEDF2]/60 active:scale-95 transition-all"
          >
            <span className="material-symbols-outlined text-[22px]">featured_seasonal_and_gifts</span>
          </button>

          <button
            aria-label="Notifications"
            onClick={onOpenNotifications}
            className="w-10 h-10 rounded-full flex items-center justify-center text-[#404754] hover:text-[#1A1C1F] hover:bg-[#EDEDF2]/60 active:scale-95 transition-all relative"
          >
            <span className="material-symbols-outlined text-[22px]">notifications</span>
            {unreadCount > 0 && (
              <span className="absolute top-2.5 right-2.5 w-2 h-2 rounded-full bg-[#FF005E] animate-pulse" />
            )}
          </button>

          <button
            aria-label="User Profile"
            onClick={onOpenProfile}
            className="w-8 h-8 rounded-full bg-[#b90042] hover:bg-[#e70054] flex items-center justify-center ml-1 shadow-[0_2px_8px_rgba(185,0,66,0.25)] active:scale-95 transition-transform"
          >
            <span className="material-symbols-outlined text-white text-[18px]">person</span>
          </button>
        </div>
      </div>
    </header>
  );
};

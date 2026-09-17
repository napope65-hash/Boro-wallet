import React from 'react';

export type NavTab = 'home' | 'wallet' | 'swap' | 'profile';

interface BottomNavProps {
  currentTab: NavTab;
  onTabChange: (tab: NavTab) => void;
}

export const BottomNav: React.FC<BottomNavProps> = ({ currentTab, onTabChange }) => {
  const tabs: { id: NavTab; label: string; icon: string }[] = [
    { id: 'home', label: 'Home', icon: 'home' },
    { id: 'wallet', label: 'Wallet', icon: 'account_balance_wallet' },
    { id: 'swap', label: 'Swap', icon: 'swap_horiz' },
    { id: 'profile', label: 'Profile', icon: 'person' },
  ];

  return (
    <div className="fixed bottom-5 inset-x-0 z-50 flex justify-center px-6 pointer-events-none pb-safe">
      <nav className="pointer-events-auto flex items-center justify-between gap-1 px-3 py-2 bg-white/95 backdrop-blur-xl rounded-full shadow-[0_8px_32px_rgba(0,0,0,0.08)] border border-black/5 max-w-xs w-full">
        {tabs.map((tab) => {
          const isActive = currentTab === tab.id;
          return (
            <button
              key={tab.id}
              onClick={() => onTabChange(tab.id)}
              className={`flex flex-col items-center justify-center w-14 h-12 rounded-full transition-all cursor-pointer ${
                isActive
                  ? 'text-[#e70054] bg-[rgba(255,0,94,0.10)] font-semibold scale-105'
                  : 'text-[#404754] hover:text-[#1A1C1F] hover:bg-[#F4F5F6]'
              }`}
            >
              <span className="material-symbols-outlined text-[22px]">{tab.icon}</span>
              <span className="text-[10px] uppercase tracking-wider mt-0.5">
                {tab.label}
              </span>
            </button>
          );
        })}
      </nav>
    </div>
  );
};

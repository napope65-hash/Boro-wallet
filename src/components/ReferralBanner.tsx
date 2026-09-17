import React from 'react';

interface ReferralBannerProps {
  onInviteClick: () => void;
}

export const ReferralBanner: React.FC<ReferralBannerProps> = ({ onInviteClick }) => {
  return (
    <div className="px-6 mt-6">
      <div className="p-4 rounded-2xl bg-[#F4F5F6] flex items-center justify-between gap-3 border border-[#EDEDF2]/60">
        <div className="flex items-center gap-3 min-w-0">
          <div className="w-10 h-10 rounded-full bg-[rgba(255,0,94,0.10)] flex items-center justify-center text-[#b90042] flex-shrink-0">
            <span className="material-symbols-outlined text-[20px]">
              featured_seasonal_and_gifts
            </span>
          </div>
          <div className="flex flex-col min-w-0">
            <span className="text-[16px] font-semibold text-[#1A1C1F] truncate">
              Earn $25 in BTC
            </span>
            <span className="text-[14px] text-[#404754] truncate">
              Invite friends to diversify with Boro
            </span>
          </div>
        </div>

        <button
          onClick={onInviteClick}
          className="px-3.5 py-1.5 rounded-full bg-[#FDFCFB] text-[#e70054] text-[12px] font-semibold shadow-xs hover:bg-[#EDEDF2] active:scale-95 transition-all flex-shrink-0 cursor-pointer"
        >
          Invite
        </button>
      </div>
    </div>
  );
};

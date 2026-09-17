import React from 'react';

interface PrimaryActionsProps {
  onBuySellClick: () => void;
  onTransferClick: () => void;
}

export const PrimaryActions: React.FC<PrimaryActionsProps> = ({
  onBuySellClick,
  onTransferClick,
}) => {
  return (
    <div className="px-6 mt-4 grid grid-cols-2 gap-2">
      <button
        onClick={onBuySellClick}
        className="flex items-center justify-center gap-2 h-13 py-3 px-4 rounded-full bg-[#e70054] hover:bg-[#b90042] text-white text-[16px] font-semibold shadow-[0_8px_24px_rgba(255,0,94,0.25)] active:scale-[0.98] transition-all cursor-pointer"
      >
        <span className="material-symbols-outlined text-[20px]">swap_calls</span>
        <span>Buy &amp; Sell</span>
      </button>

      <button
        onClick={onTransferClick}
        className="flex items-center justify-center gap-2 h-13 py-3 px-4 rounded-full bg-[#F4F5F6] hover:bg-[#EDEDF2] text-[#1A1C1F] text-[16px] font-semibold transition-all active:scale-[0.98] cursor-pointer"
      >
        <span className="material-symbols-outlined text-[20px]">arrow_outward</span>
        <span>Transfer</span>
      </button>
    </div>
  );
};

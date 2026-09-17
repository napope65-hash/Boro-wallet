import React, { useState } from 'react';

interface RewardsModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export const RewardsModal: React.FC<RewardsModalProps> = ({ isOpen, onClose }) => {
  if (!isOpen) return null;

  const [copied, setCopied] = useState(false);
  const referralCode = 'BORO-ALEX77';
  const referralLink = `https://boro.app/join/${referralCode}`;

  const handleCopy = () => {
    navigator.clipboard?.writeText(referralLink);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/50 backdrop-blur-sm animate-in fade-in">
      <div className="w-full sm:max-w-md bg-white rounded-t-3xl sm:rounded-3xl shadow-2xl flex flex-col max-h-[90vh] overflow-hidden">
        {/* Header */}
        <div className="flex items-center justify-between px-6 pt-5 pb-3 border-b border-gray-100">
          <div className="flex items-center gap-2">
            <span className="material-symbols-outlined text-[#b90042] text-[22px]">
              featured_seasonal_and_gifts
            </span>
            <h3 className="text-[18px] font-bold text-[#1A1C1F]">Invite &amp; Earn</h3>
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
          {/* Banner Hero */}
          <div className="p-5 rounded-2xl bg-gradient-to-br from-[rgba(255,0,94,0.12)] to-[#F4F5F6] text-center space-y-2">
            <div className="w-12 h-12 rounded-full bg-[#e70054] text-white flex items-center justify-center mx-auto shadow-md">
              <span className="material-symbols-outlined text-[24px]">redeem</span>
            </div>
            <h4 className="text-[20px] font-bold text-[#1A1C1F]">
              Get $25 in Bitcoin
            </h4>
            <p className="text-[13px] text-[#404754] max-w-xs mx-auto">
              Give your friends $25 in BTC when they sign up and fund $100+. You’ll both receive $25 instantly!
            </p>
          </div>

          {/* Referral Code & Link Box */}
          <div>
            <label className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider block mb-1.5">
              Your Personal Referral Link
            </label>
            <div className="flex items-center gap-2 p-2.5 bg-[#F4F5F6] rounded-xl border border-gray-200">
              <span className="font-mono text-[13px] text-[#1A1C1F] truncate flex-1 pl-1">
                {referralLink}
              </span>
              <button
                onClick={handleCopy}
                className="px-3 py-1.5 rounded-lg bg-[#e70054] hover:bg-[#b90042] text-white text-[12px] font-semibold flex items-center gap-1 shadow-xs transition-all cursor-pointer"
              >
                <span className="material-symbols-outlined text-[14px]">
                  {copied ? 'check' : 'content_copy'}
                </span>
                {copied ? 'Copied' : 'Copy'}
              </button>
            </div>
          </div>

          {/* Referral Stats */}
          <div className="grid grid-cols-2 gap-2">
            <div className="p-3 bg-[#F4F5F6] rounded-xl text-center">
              <div className="text-[20px] font-bold text-[#1A1C1F]">3</div>
              <div className="text-[11px] text-[#404754] font-medium uppercase">
                Friends Joined
              </div>
            </div>
            <div className="p-3 bg-[#F4F5F6] rounded-xl text-center">
              <div className="text-[20px] font-bold text-[#e70054]">$75.00</div>
              <div className="text-[11px] text-[#404754] font-medium uppercase">
                Earned in BTC
              </div>
            </div>
          </div>

          {/* How It Works */}
          <div className="space-y-2.5 pt-1">
            <div className="text-[12px] font-semibold text-[#1A1C1F] uppercase tracking-wider">
              How It Works
            </div>
            <div className="space-y-2 text-[13px] text-[#404754]">
              <div className="flex items-start gap-2.5">
                <span className="w-5 h-5 rounded-full bg-[#1A1C1F] text-white text-[11px] font-bold flex items-center justify-center flex-shrink-0 mt-0.5">
                  1
                </span>
                <span>Send your link or share your tag with family &amp; friends.</span>
              </div>
              <div className="flex items-start gap-2.5">
                <span className="w-5 h-5 rounded-full bg-[#1A1C1F] text-white text-[11px] font-bold flex items-center justify-center flex-shrink-0 mt-0.5">
                  2
                </span>
                <span>They create an account and deposit at least $100.</span>
              </div>
              <div className="flex items-start gap-2.5">
                <span className="w-5 h-5 rounded-full bg-[#1A1C1F] text-white text-[11px] font-bold flex items-center justify-center flex-shrink-0 mt-0.5">
                  3
                </span>
                <span>$25 worth of BTC is unlocked directly into both wallets.</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

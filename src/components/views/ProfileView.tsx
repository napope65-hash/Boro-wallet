import React, { useState } from 'react';
import { UserProfile } from '../../types';

interface ProfileViewProps {
  user: UserProfile;
  onDepositCash: (amount: number) => void;
  onWithdrawCash: (amount: number) => void;
}

export const ProfileView: React.FC<ProfileViewProps> = ({
  user,
  onDepositCash,
  onWithdrawCash,
}) => {
  const [showCashModal, setShowCashModal] = useState<'deposit' | 'withdraw' | null>(null);
  const [cashAmount, setCashAmount] = useState('1000');
  const [currency, setCurrency] = useState('USD');
  const [biometricsEnabled, setBiometricsEnabled] = useState(true);

  const handleCashAction = () => {
    const val = parseFloat(cashAmount);
    if (isNaN(val) || val <= 0) return;
    if (showCashModal === 'deposit') {
      onDepositCash(val);
    } else if (showCashModal === 'withdraw') {
      onWithdrawCash(val);
    }
    setShowCashModal(null);
    setCashAmount('1000');
  };

  return (
    <div className="flex flex-col px-6 pt-2 pb-28 max-w-lg mx-auto w-full space-y-6">
      {/* Profile Header Card */}
      <div className="p-5 rounded-3xl bg-[#F4F5F6] border border-[#EDEDF2]/60 flex items-center gap-4">
        <div className="w-14 h-14 rounded-full bg-[#b90042] text-white flex items-center justify-center text-[24px] font-bold shadow-md flex-shrink-0">
          <span className="material-symbols-outlined text-[28px]">person</span>
        </div>
        <div className="min-w-0 flex-1">
          <div className="flex items-center gap-1.5">
            <h2 className="text-[18px] font-bold text-[#1A1C1F] truncate">{user.name}</h2>
            <span
              className="material-symbols-outlined text-blue-600 text-[18px]"
              title="Verified Identity"
            >
              verified
            </span>
          </div>
          <div className="text-[13px] text-[#404754] font-mono">{user.tag}</div>
          <span className="inline-block mt-1 text-[11px] font-semibold text-[#e70054] bg-[rgba(255,0,94,0.10)] px-2 py-0.5 rounded-full">
            {user.tier}
          </span>
        </div>
      </div>

      {/* Cash Balance Card */}
      <div className="p-5 rounded-3xl bg-[#1A1C1F] text-white space-y-3 shadow-lg">
        <div className="flex justify-between items-center text-[12px] text-gray-400">
          <span>Available Cash Balance</span>
          <span className="bg-white/10 px-2 py-0.5 rounded-full text-[11px] font-semibold">
            FDIC Insured
          </span>
        </div>
        <div className="text-[32px] font-bold tracking-tight">
          ${user.cashBalance.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
        </div>
        <div className="grid grid-cols-2 gap-2 pt-1">
          <button
            onClick={() => setShowCashModal('deposit')}
            className="py-2.5 rounded-xl bg-[#e70054] hover:bg-[#b90042] text-white text-[13px] font-semibold transition-colors flex items-center justify-center gap-1 cursor-pointer"
          >
            <span className="material-symbols-outlined text-[16px]">add</span>
            Deposit Cash
          </button>
          <button
            onClick={() => setShowCashModal('withdraw')}
            className="py-2.5 rounded-xl bg-white/10 hover:bg-white/20 text-white text-[13px] font-semibold transition-colors flex items-center justify-center gap-1 cursor-pointer"
          >
            <span className="material-symbols-outlined text-[16px]">arrow_downward</span>
            Withdraw
          </button>
        </div>
      </div>

      {/* Security & Preferences */}
      <div className="space-y-2">
        <h3 className="text-[14px] font-bold text-[#1A1C1F] uppercase tracking-wider">
          Security &amp; Preferences
        </h3>
        <div className="divide-y divide-gray-100 bg-[#F4F5F6] rounded-2xl overflow-hidden text-[14px]">
          <div className="flex items-center justify-between p-3.5">
            <div className="flex items-center gap-2.5">
              <span className="material-symbols-outlined text-[#404754] text-[20px]">
                fingerprint
              </span>
              <span className="font-semibold text-[#1A1C1F]">Biometric Passkey</span>
            </div>
            <input
              type="checkbox"
              checked={biometricsEnabled}
              onChange={(e) => setBiometricsEnabled(e.target.checked)}
              className="accent-[#e70054] w-4 h-4 cursor-pointer"
            />
          </div>

          <div className="flex items-center justify-between p-3.5">
            <div className="flex items-center gap-2.5">
              <span className="material-symbols-outlined text-[#404754] text-[20px]">
                currency_exchange
              </span>
              <span className="font-semibold text-[#1A1C1F]">Display Currency</span>
            </div>
            <select
              value={currency}
              onChange={(e) => setCurrency(e.target.value)}
              className="bg-white px-2.5 py-1 rounded-lg border border-gray-200 text-[12px] font-bold focus:outline-none"
            >
              <option value="USD">USD ($)</option>
              <option value="EUR">EUR (€)</option>
              <option value="GBP">GBP (£)</option>
            </select>
          </div>

          <div className="flex items-center justify-between p-3.5">
            <div className="flex items-center gap-2.5">
              <span className="material-symbols-outlined text-[#404754] text-[20px]">
                support_agent
              </span>
              <span className="font-semibold text-[#1A1C1F]">24/7 Priority Support</span>
            </div>
            <span className="text-[12px] font-semibold text-[#e70054]">Online</span>
          </div>
        </div>
      </div>

      {/* Cash Modal Sheet */}
      {showCashModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm animate-in fade-in">
          <div className="w-full max-w-sm bg-white rounded-3xl p-6 shadow-2xl space-y-4">
            <div className="flex justify-between items-center">
              <h3 className="text-[18px] font-bold text-[#1A1C1F] capitalize">
                {showCashModal} Cash
              </h3>
              <button
                onClick={() => setShowCashModal(null)}
                className="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center text-gray-500"
              >
                <span className="material-symbols-outlined text-[18px]">close</span>
              </button>
            </div>

            <div>
              <label className="text-[12px] font-semibold text-[#404754] block mb-1">
                Amount (USD)
              </label>
              <input
                type="number"
                value={cashAmount}
                onChange={(e) => setCashAmount(e.target.value)}
                className="w-full p-3 rounded-xl border border-gray-200 text-[20px] font-bold text-[#1A1C1F]"
              />
            </div>

            <button
              onClick={handleCashAction}
              className="w-full py-3.5 rounded-full bg-[#e70054] text-white font-semibold text-[15px] hover:bg-[#b90042] transition-colors"
            >
              Confirm {showCashModal}
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

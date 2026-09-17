import React, { useState } from 'react';
import { Asset } from '../../types';

interface BuySellModalProps {
  isOpen: boolean;
  onClose: () => void;
  assets: Asset[];
  initialAsset?: Asset;
  cashBalance: number;
  onExecuteTrade: (
    type: 'buy' | 'sell',
    assetId: string,
    amountUSD: number,
    units: number
  ) => void;
}

export const BuySellModal: React.FC<BuySellModalProps> = ({
  isOpen,
  onClose,
  assets,
  initialAsset,
  cashBalance,
  onExecuteTrade,
}) => {
  if (!isOpen) return null;

  const [mode, setMode] = useState<'buy' | 'sell'>('buy');
  const [selectedAssetId, setSelectedAssetId] = useState<string>(
    initialAsset?.id || assets[0]?.id || 'btc'
  );
  const [amountInput, setAmountInput] = useState<string>('500');
  const [paymentMethod, setPaymentMethod] = useState<'cash' | 'bank' | 'apple_pay'>('cash');
  const [isSuccess, setIsSuccess] = useState<boolean>(false);
  const [errorMsg, setErrorMsg] = useState<string>('');

  const currentAsset = assets.find((a) => a.id === selectedAssetId) || assets[0];
  const parsedAmount = parseFloat(amountInput) || 0;
  const calculatedUnits = currentAsset
    ? parsedAmount / currentAsset.currentPrice
    : 0;

  const handlePercentage = (pct: number) => {
    if (mode === 'buy') {
      const val = (cashBalance * pct) / 100;
      setAmountInput(val.toFixed(2));
    } else {
      const maxUnits = currentAsset.sharesOrTokens;
      const targetUnits = (maxUnits * pct) / 100;
      const val = targetUnits * currentAsset.currentPrice;
      setAmountInput(val.toFixed(2));
    }
    setErrorMsg('');
  };

  const handleConfirm = () => {
    if (parsedAmount <= 0) {
      setErrorMsg('Please enter a valid amount');
      return;
    }

    if (mode === 'buy' && paymentMethod === 'cash' && parsedAmount > cashBalance) {
      setErrorMsg(`Insufficient cash balance ($${cashBalance.toFixed(2)} available)`);
      return;
    }

    if (mode === 'sell') {
      const maxSellUSD = currentAsset.sharesOrTokens * currentAsset.currentPrice;
      if (parsedAmount > maxSellUSD + 0.01) {
        setErrorMsg(
          `You only hold ${currentAsset.sharesOrTokens} ${currentAsset.unitLabel} ($${maxSellUSD.toFixed(2)})`
        );
        return;
      }
    }

    onExecuteTrade(mode, currentAsset.id, parsedAmount, calculatedUnits);
    setIsSuccess(true);
    setTimeout(() => {
      setIsSuccess(false);
      onClose();
    }, 1800);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/50 backdrop-blur-sm animate-in fade-in">
      <div className="w-full sm:max-w-md bg-white rounded-t-3xl sm:rounded-3xl shadow-2xl flex flex-col max-h-[90vh] overflow-hidden">
        {/* Header */}
        <div className="flex items-center justify-between px-6 pt-5 pb-3 border-b border-gray-100">
          <div className="flex bg-[#F4F5F6] p-1 rounded-full">
            <button
              onClick={() => {
                setMode('buy');
                setErrorMsg('');
              }}
              className={`px-4 py-1.5 rounded-full text-[14px] font-semibold transition-all ${
                mode === 'buy'
                  ? 'bg-white text-[#e70054] shadow-xs'
                  : 'text-[#404754]'
              }`}
            >
              Buy
            </button>
            <button
              onClick={() => {
                setMode('sell');
                setErrorMsg('');
              }}
              className={`px-4 py-1.5 rounded-full text-[14px] font-semibold transition-all ${
                mode === 'sell'
                  ? 'bg-white text-[#e70054] shadow-xs'
                  : 'text-[#404754]'
              }`}
            >
              Sell
            </button>
          </div>

          <button
            onClick={onClose}
            className="w-8 h-8 rounded-full bg-[#F4F5F6] hover:bg-[#EDEDF2] flex items-center justify-center text-[#404754] transition-colors"
          >
            <span className="material-symbols-outlined text-[18px]">close</span>
          </button>
        </div>

        {isSuccess ? (
          <div className="p-10 flex flex-col items-center justify-center text-center space-y-4">
            <div className="w-16 h-16 rounded-full bg-[rgba(255,0,94,0.12)] text-[#FF005E] flex items-center justify-center animate-bounce">
              <span className="material-symbols-outlined text-[36px]">check_circle</span>
            </div>
            <div>
              <h3 className="text-[20px] font-bold text-[#1A1C1F]">Order Executed!</h3>
              <p className="text-[14px] text-[#404754] mt-1">
                Successfully {mode === 'buy' ? 'purchased' : 'sold'}{' '}
                <span className="font-semibold text-[#1A1C1F]">
                  {calculatedUnits.toFixed(4)} {currentAsset.symbol}
                </span>{' '}
                for ${parsedAmount.toFixed(2)}
              </p>
            </div>
          </div>
        ) : (
          <div className="p-6 overflow-y-auto space-y-5">
            {/* Asset Selector */}
            <div>
              <label className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider block mb-2">
                Select Asset
              </label>
              <div className="grid grid-cols-3 gap-2">
                {assets.map((asset) => (
                  <button
                    key={asset.id}
                    onClick={() => {
                      setSelectedAssetId(asset.id);
                      setErrorMsg('');
                    }}
                    className={`flex items-center gap-2 p-2.5 rounded-xl border text-left transition-all ${
                      asset.id === selectedAssetId
                        ? 'border-[#e70054] bg-[rgba(255,0,94,0.04)] ring-1 ring-[#e70054]'
                        : 'border-gray-200 hover:border-gray-300'
                    }`}
                  >
                    <span
                      style={{ backgroundColor: asset.iconBg, color: asset.iconColor }}
                      className="w-7 h-7 rounded-full flex items-center justify-center text-[12px] font-bold flex-shrink-0"
                    >
                      {asset.symbol.slice(0, 3)}
                    </span>
                    <div className="min-w-0">
                      <div className="text-[13px] font-semibold text-[#1A1C1F] truncate">
                        {asset.symbol}
                      </div>
                      <div className="text-[11px] text-[#404754] truncate">
                        ${asset.currentPrice.toLocaleString()}
                      </div>
                    </div>
                  </button>
                ))}
              </div>
            </div>

            {/* Amount Input */}
            <div className="bg-[#F4F5F6] p-4 rounded-2xl">
              <div className="flex items-center justify-between text-[12px] text-[#404754] mb-1 font-medium">
                <span>Amount in USD</span>
                <span>
                  {mode === 'buy'
                    ? `Cash: $${cashBalance.toFixed(2)}`
                    : `Holding: ${currentAsset.sharesOrTokens} ${currentAsset.unitLabel}`}
                </span>
              </div>
              <div className="flex items-center justify-between">
                <span className="text-[28px] font-bold text-[#1A1C1F]">$</span>
                <input
                  type="number"
                  step="any"
                  value={amountInput}
                  onChange={(e) => {
                    setAmountInput(e.target.value);
                    setErrorMsg('');
                  }}
                  className="bg-transparent text-[32px] font-bold text-right text-[#1A1C1F] focus:outline-none w-full"
                  placeholder="0.00"
                />
              </div>
              <div className="text-right text-[13px] text-[#404754] mt-1">
                ≈ {calculatedUnits.toFixed(5)} {currentAsset.symbol}
              </div>

              {/* Quick % buttons */}
              <div className="grid grid-cols-4 gap-2 mt-3 pt-3 border-t border-gray-200/70">
                {[25, 50, 75, 100].map((pct) => (
                  <button
                    key={pct}
                    type="button"
                    onClick={() => handlePercentage(pct)}
                    className="py-1 rounded-lg bg-white text-[12px] font-semibold text-[#1A1C1F] shadow-xs hover:bg-[#e70054] hover:text-white transition-colors"
                  >
                    {pct === 100 ? 'MAX' : `${pct}%`}
                  </button>
                ))}
              </div>
            </div>

            {/* Payment Method / Settlement Destination */}
            {mode === 'buy' && (
              <div>
                <label className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider block mb-2">
                  Pay With
                </label>
                <div className="space-y-2">
                  <label className="flex items-center justify-between p-3 rounded-xl border border-gray-200 cursor-pointer hover:bg-gray-50">
                    <div className="flex items-center gap-2.5">
                      <input
                        type="radio"
                        checked={paymentMethod === 'cash'}
                        onChange={() => setPaymentMethod('cash')}
                        className="accent-[#e70054]"
                      />
                      <div>
                        <div className="text-[14px] font-semibold text-[#1A1C1F]">
                          Boro Cash Balance
                        </div>
                        <div className="text-[12px] text-[#404754]">
                          Available: ${cashBalance.toFixed(2)}
                        </div>
                      </div>
                    </div>
                    <span className="text-[11px] font-semibold bg-green-100 text-green-700 px-2 py-0.5 rounded-full">
                      Instant
                    </span>
                  </label>

                  <label className="flex items-center justify-between p-3 rounded-xl border border-gray-200 cursor-pointer hover:bg-gray-50">
                    <div className="flex items-center gap-2.5">
                      <input
                        type="radio"
                        checked={paymentMethod === 'bank'}
                        onChange={() => setPaymentMethod('bank')}
                        className="accent-[#e70054]"
                      />
                      <div>
                        <div className="text-[14px] font-semibold text-[#1A1C1F]">
                          Chase Premier Checking (*8492)
                        </div>
                        <div className="text-[12px] text-[#404754]">ACH Transfer</div>
                      </div>
                    </div>
                    <span className="text-[11px] font-semibold bg-gray-100 text-[#404754] px-2 py-0.5 rounded-full">
                      Free
                    </span>
                  </label>
                </div>
              </div>
            )}

            {/* Summary Line */}
            <div className="p-3 bg-[#F4F5F6] rounded-xl text-[12px] space-y-1 text-[#404754]">
              <div className="flex justify-between">
                <span>Trading Fee:</span>
                <span className="font-semibold text-green-600">$0.00 (Zero Commission)</span>
              </div>
              <div className="flex justify-between">
                <span>Estimated Price:</span>
                <span className="font-semibold text-[#1A1C1F]">
                  ${currentAsset.currentPrice.toLocaleString()} per {currentAsset.symbol}
                </span>
              </div>
            </div>

            {errorMsg && (
              <div className="text-[13px] text-red-600 font-medium bg-red-50 p-2.5 rounded-xl text-center">
                {errorMsg}
              </div>
            )}

            {/* Action CTA Button */}
            <button
              onClick={handleConfirm}
              className="w-full py-3.5 rounded-full bg-[#e70054] hover:bg-[#b90042] text-white text-[16px] font-semibold shadow-[0_8px_24px_rgba(255,0,94,0.25)] active:scale-[0.98] transition-all cursor-pointer"
            >
              Confirm {mode === 'buy' ? 'Purchase' : 'Sale'}
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

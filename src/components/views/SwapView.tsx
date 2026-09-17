import React, { useState } from 'react';
import { Asset } from '../../types';

interface SwapViewProps {
  assets: Asset[];
  cashBalance: number;
  onExecuteTrade: (
    type: 'buy' | 'sell',
    assetId: string,
    amountUSD: number,
    units: number
  ) => void;
}

export const SwapView: React.FC<SwapViewProps> = ({
  assets,
  onExecuteTrade,
}) => {
  const [fromAssetId, setFromAssetId] = useState<string>(assets[0]?.id || 'btc');
  const [toAssetId, setToAssetId] = useState<string>(assets[3]?.id || 'eth');
  const [fromAmount, setFromAmount] = useState<string>('0.05');
  const [isSuccess, setIsSuccess] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  const fromAsset = assets.find((a) => a.id === fromAssetId) || assets[0];
  const toAsset = assets.find((a) => a.id === toAssetId) || assets[1];

  const parsedFrom = parseFloat(fromAmount) || 0;
  const fromValueUSD = parsedFrom * fromAsset.currentPrice;
  const toAmount = toAsset ? fromValueUSD / toAsset.currentPrice : 0;
  const rate = fromAsset.currentPrice / toAsset.currentPrice;

  const handleFlip = () => {
    const prevFrom = fromAssetId;
    setFromAssetId(toAssetId);
    setToAssetId(prevFrom);
    setErrorMsg('');
  };

  const handleSwap = () => {
    if (parsedFrom <= 0) {
      setErrorMsg('Please enter a valid amount');
      return;
    }
    if (parsedFrom > fromAsset.sharesOrTokens) {
      setErrorMsg(`Insufficient ${fromAsset.symbol} balance (${fromAsset.sharesOrTokens} available)`);
      return;
    }

    // Execute internal sell of fromAsset and buy of toAsset
    onExecuteTrade('sell', fromAsset.id, fromValueUSD, parsedFrom);
    onExecuteTrade('buy', toAsset.id, fromValueUSD, toAmount);

    setIsSuccess(true);
    setTimeout(() => {
      setIsSuccess(false);
      setFromAmount('');
    }, 2000);
  };

  return (
    <div className="flex flex-col px-6 pt-2 pb-28 max-w-lg mx-auto w-full">
      <div className="mb-4">
        <h2 className="text-[24px] font-bold text-[#1A1C1F]">Instant Swap</h2>
        <p className="text-[14px] text-[#404754]">
          Zero-slippage conversion across crypto, stocks, and ETFs
        </p>
      </div>

      {isSuccess ? (
        <div className="p-8 rounded-3xl bg-[#F4F5F6] text-center space-y-3 animate-in fade-in">
          <div className="w-16 h-16 rounded-full bg-green-100 text-green-600 flex items-center justify-center mx-auto animate-bounce">
            <span className="material-symbols-outlined text-[36px]">swap_horiz</span>
          </div>
          <h3 className="text-[20px] font-bold text-[#1A1C1F]">Swap Complete!</h3>
          <p className="text-[14px] text-[#404754]">
            Swapped {parsedFrom} {fromAsset.symbol} for{' '}
            <span className="font-semibold text-[#1A1C1F]">
              {toAmount.toFixed(4)} {toAsset.symbol}
            </span>
          </p>
        </div>
      ) : (
        <div className="space-y-3">
          {/* From Card */}
          <div className="p-4 rounded-2xl bg-[#F4F5F6] border border-gray-200/60">
            <div className="flex justify-between text-[12px] font-semibold text-[#404754] mb-2">
              <span className="uppercase tracking-wider">You Pay</span>
              <button
                type="button"
                onClick={() => setFromAmount(fromAsset.sharesOrTokens.toString())}
                className="text-[#e70054] hover:underline"
              >
                Available: {fromAsset.sharesOrTokens} {fromAsset.unitLabel}
              </button>
            </div>

            <div className="flex items-center justify-between gap-3">
              <input
                type="number"
                step="any"
                placeholder="0.0"
                value={fromAmount}
                onChange={(e) => {
                  setFromAmount(e.target.value);
                  setErrorMsg('');
                }}
                className="bg-transparent text-[28px] font-bold text-[#1A1C1F] focus:outline-none w-full"
              />

              <select
                value={fromAssetId}
                onChange={(e) => {
                  setFromAssetId(e.target.value);
                  setErrorMsg('');
                }}
                className="bg-white px-3 py-2 rounded-xl text-[14px] font-bold text-[#1A1C1F] border border-gray-200 shadow-xs focus:outline-none"
              >
                {assets.map((a) => (
                  <option key={a.id} value={a.id} disabled={a.id === toAssetId}>
                    {a.symbol}
                  </option>
                ))}
              </select>
            </div>

            <div className="text-[12px] text-[#404754] mt-1">
              ≈ ${fromValueUSD.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })} USD
            </div>
          </div>

          {/* Flip Button */}
          <div className="flex justify-center -my-2 relative z-10">
            <button
              onClick={handleFlip}
              className="w-10 h-10 rounded-full bg-white border border-gray-200 shadow-md flex items-center justify-center text-[#e70054] hover:scale-110 active:rotate-180 transition-all cursor-pointer"
            >
              <span className="material-symbols-outlined text-[20px]">swap_vert</span>
            </button>
          </div>

          {/* To Card */}
          <div className="p-4 rounded-2xl bg-[#F4F5F6] border border-gray-200/60">
            <div className="flex justify-between text-[12px] font-semibold text-[#404754] mb-2">
              <span className="uppercase tracking-wider">You Receive</span>
              <span>
                Bal: {toAsset.sharesOrTokens} {toAsset.unitLabel}
              </span>
            </div>

            <div className="flex items-center justify-between gap-3">
              <div className="text-[28px] font-bold text-[#1A1C1F] truncate">
                {toAmount ? toAmount.toFixed(4) : '0.00'}
              </div>

              <select
                value={toAssetId}
                onChange={(e) => {
                  setToAssetId(e.target.value);
                  setErrorMsg('');
                }}
                className="bg-white px-3 py-2 rounded-xl text-[14px] font-bold text-[#1A1C1F] border border-gray-200 shadow-xs focus:outline-none"
              >
                {assets.map((a) => (
                  <option key={a.id} value={a.id} disabled={a.id === fromAssetId}>
                    {a.symbol}
                  </option>
                ))}
              </select>
            </div>

            <div className="text-[12px] text-[#404754] mt-1">
              ≈ ${fromValueUSD.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })} USD
            </div>
          </div>

          {/* Details Pill */}
          <div className="p-3.5 rounded-2xl bg-white border border-gray-100 text-[12px] space-y-1.5 text-[#404754]">
            <div className="flex justify-between">
              <span>Exchange Rate:</span>
              <span className="font-semibold text-[#1A1C1F]">
                1 {fromAsset.symbol} ≈ {rate.toFixed(4)} {toAsset.symbol}
              </span>
            </div>
            <div className="flex justify-between">
              <span>Network Routing:</span>
              <span className="font-semibold text-green-600">Direct Smart Router ($0 Fee)</span>
            </div>
            <div className="flex justify-between">
              <span>Guaranteed Slippage:</span>
              <span className="font-semibold text-[#1A1C1F]">0.1%</span>
            </div>
          </div>

          {errorMsg && (
            <div className="text-[13px] text-red-600 font-medium bg-red-50 p-2.5 rounded-xl text-center">
              {errorMsg}
            </div>
          )}

          <button
            onClick={handleSwap}
            className="w-full py-4 rounded-full bg-[#e70054] hover:bg-[#b90042] text-white text-[16px] font-semibold shadow-[0_8px_24px_rgba(255,0,94,0.25)] active:scale-[0.98] transition-all cursor-pointer mt-2"
          >
            Review &amp; Execute Swap
          </button>
        </div>
      )}
    </div>
  );
};

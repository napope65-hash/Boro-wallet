import React, { useState } from 'react';
import { Asset } from '../../types';

interface TransferModalProps {
  isOpen: boolean;
  onClose: () => void;
  assets: Asset[];
  onExecuteSend?: (assetId: string, amount: number, recipient: string) => void;
}

export const TransferModal: React.FC<TransferModalProps> = ({
  isOpen,
  onClose,
  assets,
  onExecuteSend,
}) => {
  if (!isOpen) return null;

  const [tab, setTab] = useState<'send' | 'receive'>('send');
  const [selectedAssetId, setSelectedAssetId] = useState<string>('btc');
  const [recipient, setRecipient] = useState('');
  const [amount, setAmount] = useState('');
  const [copied, setCopied] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  const currentAsset = assets.find((a) => a.id === selectedAssetId) || assets[0];

  // Simulated deterministic address
  const getAddress = (symbol: string) => {
    switch (symbol) {
      case 'BTC':
        return 'bc1q9x7h2m5g8e3l4k7j9p0w1v6z8c4y7t2a';
      case 'ETH':
        return '0x71C...B29082F9934B8C1D6E76';
      default:
        return 'boro.id/u/alex_sterling';
    }
  };

  const handleCopy = () => {
    navigator.clipboard?.writeText(getAddress(currentAsset.symbol));
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const handleSend = () => {
    const num = parseFloat(amount);
    if (!recipient.trim()) {
      setErrorMsg('Please specify a recipient or wallet address');
      return;
    }
    if (isNaN(num) || num <= 0) {
      setErrorMsg('Please enter a valid transfer amount');
      return;
    }
    if (num > currentAsset.sharesOrTokens) {
      setErrorMsg(
        `Insufficient balance. You hold ${currentAsset.sharesOrTokens} ${currentAsset.unitLabel}`
      );
      return;
    }

    onExecuteSend?.(currentAsset.id, num, recipient);
    setIsSuccess(true);
    setTimeout(() => {
      setIsSuccess(false);
      onClose();
    }, 1800);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/50 backdrop-blur-sm animate-in fade-in">
      <div className="w-full sm:max-w-md bg-white rounded-t-3xl sm:rounded-3xl shadow-2xl flex flex-col max-h-[90vh] overflow-hidden">
        {/* Header Tabs */}
        <div className="flex items-center justify-between px-6 pt-5 pb-3 border-b border-gray-100">
          <div className="flex bg-[#F4F5F6] p-1 rounded-full">
            <button
              onClick={() => {
                setTab('send');
                setErrorMsg('');
              }}
              className={`px-5 py-1.5 rounded-full text-[14px] font-semibold transition-all ${
                tab === 'send'
                  ? 'bg-white text-[#e70054] shadow-xs'
                  : 'text-[#404754]'
              }`}
            >
              Send
            </button>
            <button
              onClick={() => {
                setTab('receive');
                setErrorMsg('');
              }}
              className={`px-5 py-1.5 rounded-full text-[14px] font-semibold transition-all ${
                tab === 'receive'
                  ? 'bg-white text-[#e70054] shadow-xs'
                  : 'text-[#404754]'
              }`}
            >
              Receive
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
            <div className="w-16 h-16 rounded-full bg-green-100 text-green-600 flex items-center justify-center animate-bounce">
              <span className="material-symbols-outlined text-[36px]">send</span>
            </div>
            <div>
              <h3 className="text-[20px] font-bold text-[#1A1C1F]">Transfer Sent!</h3>
              <p className="text-[14px] text-[#404754] mt-1">
                Sent {amount} {currentAsset.symbol} to{' '}
                <span className="font-semibold text-[#1A1C1F]">{recipient}</span>
              </p>
            </div>
          </div>
        ) : tab === 'send' ? (
          <div className="p-6 overflow-y-auto space-y-4">
            {/* Asset to Send */}
            <div>
              <label className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider block mb-2">
                Choose Asset
              </label>
              <select
                value={selectedAssetId}
                onChange={(e) => {
                  setSelectedAssetId(e.target.value);
                  setErrorMsg('');
                }}
                className="w-full p-3 rounded-xl border border-gray-200 text-[15px] font-semibold text-[#1A1C1F] focus:outline-none focus:border-[#e70054] bg-[#F4F5F6]"
              >
                {assets.map((asset) => (
                  <option key={asset.id} value={asset.id}>
                    {asset.name} ({asset.symbol}) — {asset.sharesOrTokens} {asset.unitLabel}
                  </option>
                ))}
              </select>
            </div>

            {/* Recipient */}
            <div>
              <label className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider block mb-2">
                To (Address, Tag, or Email)
              </label>
              <div className="relative">
                <input
                  type="text"
                  placeholder="$boro_tag, 0x..., or email"
                  value={recipient}
                  onChange={(e) => {
                    setRecipient(e.target.value);
                    setErrorMsg('');
                  }}
                  className="w-full p-3 pr-10 rounded-xl border border-gray-200 text-[14px] text-[#1A1C1F] focus:outline-none focus:border-[#e70054]"
                />
                <span className="absolute right-3 top-3.5 material-symbols-outlined text-gray-400 text-[20px]">
                  qr_code_scanner
                </span>
              </div>
            </div>

            {/* Amount */}
            <div>
              <div className="flex justify-between text-[12px] font-semibold text-[#404754] mb-2">
                <span className="uppercase tracking-wider">Amount</span>
                <button
                  type="button"
                  onClick={() => setAmount(currentAsset.sharesOrTokens.toString())}
                  className="text-[#e70054] hover:underline"
                >
                  Max ({currentAsset.sharesOrTokens} {currentAsset.unitLabel})
                </button>
              </div>
              <div className="relative">
                <input
                  type="number"
                  step="any"
                  placeholder="0.00"
                  value={amount}
                  onChange={(e) => {
                    setAmount(e.target.value);
                    setErrorMsg('');
                  }}
                  className="w-full p-3 text-[18px] font-bold text-[#1A1C1F] rounded-xl border border-gray-200 focus:outline-none focus:border-[#e70054]"
                />
                <span className="absolute right-3 top-3 text-[14px] font-semibold text-[#404754]">
                  {currentAsset.symbol}
                </span>
              </div>
              {amount && (
                <div className="text-[12px] text-[#404754] mt-1 text-right">
                  ≈ ${(parseFloat(amount) * currentAsset.currentPrice || 0).toFixed(2)} USD
                </div>
              )}
            </div>

            {/* Fee note */}
            <div className="p-3 bg-[#F4F5F6] rounded-xl text-[12px] flex justify-between text-[#404754]">
              <span>Network fee:</span>
              <span className="font-semibold text-green-600">Free (Sponsored by Boro)</span>
            </div>

            {errorMsg && (
              <div className="text-[13px] text-red-600 font-medium bg-red-50 p-2.5 rounded-xl text-center">
                {errorMsg}
              </div>
            )}

            <button
              onClick={handleSend}
              className="w-full py-3.5 rounded-full bg-[#e70054] hover:bg-[#b90042] text-white text-[16px] font-semibold shadow-[0_8px_24px_rgba(255,0,94,0.25)] active:scale-[0.98] transition-all cursor-pointer"
            >
              Send {currentAsset.symbol}
            </button>
          </div>
        ) : (
          <div className="p-6 overflow-y-auto space-y-5 flex flex-col items-center text-center">
            {/* Asset Picker for Receive */}
            <div className="w-full">
              <label className="text-[12px] font-semibold text-[#404754] uppercase tracking-wider block mb-2 text-left">
                Receive Asset
              </label>
              <select
                value={selectedAssetId}
                onChange={(e) => setSelectedAssetId(e.target.value)}
                className="w-full p-3 rounded-xl border border-gray-200 text-[15px] font-semibold text-[#1A1C1F] focus:outline-none focus:border-[#e70054] bg-[#F4F5F6]"
              >
                {assets.map((asset) => (
                  <option key={asset.id} value={asset.id}>
                    {asset.name} ({asset.symbol})
                  </option>
                ))}
              </select>
            </div>

            {/* QR Code */}
            <div className="p-4 bg-white border-2 border-dashed border-gray-200 rounded-3xl shadow-sm">
              <svg className="w-48 h-48" viewBox="0 0 100 100">
                {/* SVG mock QR pattern with rounded corner markers */}
                <rect width="100" height="100" fill="#ffffff" />
                {/* Top Left Marker */}
                <rect x="10" y="10" width="24" height="24" rx="4" fill="#1A1C1F" />
                <rect x="14" y="14" width="16" height="16" rx="2" fill="#ffffff" />
                <rect x="18" y="18" width="8" height="8" rx="1" fill="#1A1C1F" />

                {/* Top Right Marker */}
                <rect x="66" y="10" width="24" height="24" rx="4" fill="#1A1C1F" />
                <rect x="70" y="14" width="16" height="16" rx="2" fill="#ffffff" />
                <rect x="74" y="18" width="8" height="8" rx="1" fill="#1A1C1F" />

                {/* Bottom Left Marker */}
                <rect x="10" y="66" width="24" height="24" rx="4" fill="#1A1C1F" />
                <rect x="14" y="70" width="16" height="16" rx="2" fill="#ffffff" />
                <rect x="18" y="74" width="8" height="8" rx="1" fill="#1A1C1F" />

                {/* Data Grid Cells */}
                <rect x="40" y="12" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="50" y="12" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="40" y="24" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="54" y="24" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="14" y="42" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="24" y="42" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="36" y="38" width="8" height="8" rx="1" fill="#1A1C1F" />
                <rect x="52" y="38" width="8" height="8" rx="1" fill="#1A1C1F" />
                <rect x="68" y="42" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="80" y="42" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="42" y="54" width="8" height="8" rx="1" fill="#1A1C1F" />
                <rect x="56" y="54" width="8" height="8" rx="1" fill="#1A1C1F" />
                <rect x="70" y="54" width="8" height="8" rx="1" fill="#1A1C1F" />
                <rect x="40" y="72" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="52" y="72" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="68" y="72" width="6" height="6" rx="1" fill="#1A1C1F" />
                <rect x="82" y="72" width="6" height="6" rx="1" fill="#1A1C1F" />

                {/* Center Badge */}
                <circle cx="50" cy="50" r="10" fill="#FF005E" />
                <text
                  x="50"
                  y="54"
                  fill="#ffffff"
                  fontSize="10"
                  fontWeight="bold"
                  textAnchor="middle"
                >
                  B
                </text>
              </svg>
            </div>

            {/* Address Box with 1-click copy */}
            <div className="w-full">
              <div className="text-[12px] text-[#404754] mb-1">Your {currentAsset.symbol} Address</div>
              <div
                onClick={handleCopy}
                className="flex items-center justify-between p-3 bg-[#F4F5F6] hover:bg-[#EDEDF2] rounded-xl cursor-pointer transition-colors"
              >
                <span className="font-mono text-[13px] text-[#1A1C1F] truncate mr-2">
                  {getAddress(currentAsset.symbol)}
                </span>
                <span className="text-[#e70054] text-[13px] font-semibold flex items-center gap-1 flex-shrink-0">
                  <span className="material-symbols-outlined text-[16px]">
                    {copied ? 'check' : 'content_copy'}
                  </span>
                  {copied ? 'Copied' : 'Copy'}
                </span>
              </div>
            </div>

            <p className="text-[12px] text-[#404754]">
              Send only <span className="font-semibold">{currentAsset.name} ({currentAsset.symbol})</span> to this address. Incoming transfers are credited with 0 confirmations.
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

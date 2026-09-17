import React from 'react';
import { NewsArticle } from '../../types';

interface ArticleModalProps {
  article: NewsArticle | null;
  isOpen: boolean;
  onClose: () => void;
}

export const ArticleModal: React.FC<ArticleModalProps> = ({
  article,
  isOpen,
  onClose,
}) => {
  if (!isOpen || !article) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/50 backdrop-blur-sm animate-in fade-in">
      <div className="w-full sm:max-w-md bg-white rounded-t-3xl sm:rounded-3xl shadow-2xl flex flex-col max-h-[90vh] overflow-hidden">
        {/* Header */}
        <div className="flex items-center justify-between px-6 pt-5 pb-3 border-b border-gray-100">
          <div className="flex items-center gap-2">
            <span className="px-2.5 py-0.5 rounded-full text-[11px] font-semibold bg-[#dde2f2] text-[#404754]">
              {article.category}
            </span>
            <span className="text-[12px] text-[#404754]">
              {article.source} • {article.timeAgo}
            </span>
          </div>

          <button
            onClick={onClose}
            className="w-8 h-8 rounded-full bg-[#F4F5F6] hover:bg-[#EDEDF2] flex items-center justify-center text-[#404754] transition-colors"
          >
            <span className="material-symbols-outlined text-[18px]">close</span>
          </button>
        </div>

        {/* Article Body */}
        <div className="p-6 overflow-y-auto space-y-4">
          <img
            className="w-full h-48 rounded-2xl object-cover shadow-sm"
            alt={article.imageAlt}
            src={article.imageUrl}
            referrerPolicy="no-referrer"
          />

          <h2 className="text-[20px] font-bold text-[#1A1C1F] leading-snug">
            {article.title}
          </h2>

          <div className="flex items-center gap-3 text-[13px] text-[#404754]">
            <span className="inline-flex items-center gap-1 font-semibold text-green-700 bg-green-50 px-2 py-0.5 rounded-full">
              <span className="material-symbols-outlined text-[14px]">insights</span>
              {article.sentiment}
            </span>
            <span>{article.readTime}</span>
          </div>

          <p className="text-[15px] text-[#404754] leading-relaxed">
            {article.summary}
          </p>

          <div className="p-4 rounded-2xl bg-[#F4F5F6] space-y-2">
            <div className="text-[12px] font-semibold text-[#1A1C1F] uppercase tracking-wider">
              Key Takeaways
            </div>
            <ul className="text-[13px] text-[#404754] space-y-1.5 list-disc list-inside">
              <li>Strong macro liquidity momentum supporting benchmark assets.</li>
              <li>Exchange outflows highlight aggressive long-term accumulation.</li>
              <li>Zero-commission institutional routing available on Boro Wallet.</li>
            </ul>
          </div>

          <div className="pt-2">
            <button
              onClick={onClose}
              className="w-full py-3 rounded-full bg-[#1A1C1F] text-white text-[14px] font-semibold hover:bg-black transition-colors"
            >
              Done Reading
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

import React from 'react';
import { NewsArticle } from '../types';

interface MarketIntelligenceProps {
  articles: NewsArticle[];
  onSelectArticle: (article: NewsArticle) => void;
  onSeeAll: () => void;
  isExpanded?: boolean;
}

export const MarketIntelligence: React.FC<MarketIntelligenceProps> = ({
  articles,
  onSelectArticle,
  onSeeAll,
  isExpanded = false,
}) => {
  return (
    <section className="mt-8 px-6">
      {/* Header */}
      <div className="flex items-center justify-between mb-3">
        <div className="flex items-center gap-2">
          <span className="text-[20px] font-semibold text-[#1A1C1F]">
            Market Intelligence
          </span>
          <span className="material-symbols-outlined text-[#b90042] text-[18px]">
            bolt
          </span>
        </div>
        <button
          onClick={onSeeAll}
          className="text-[14px] text-[#e70054] font-semibold hover:opacity-80 transition-opacity cursor-pointer"
        >
          {isExpanded ? 'Show less' : 'See all'}
        </button>
      </div>

      {/* News Feed Cards Stack */}
      <div className="flex flex-col gap-3">
        {articles.map((article, index) => {
          const isHighlight = index === 1; // Crypto has the pink tint badge
          return (
            <article
              key={article.id}
              onClick={() => onSelectArticle(article)}
              className="p-4 rounded-2xl bg-[#F4F5F6] hover:bg-[#EDEDF2] transition-all flex flex-col gap-2.5 cursor-pointer group active:scale-[0.99]"
            >
              <div className="flex items-center justify-between">
                <span
                  className={`px-2.5 py-0.5 rounded-full text-[11px] font-semibold ${
                    isHighlight
                      ? 'bg-[rgba(255,0,94,0.10)] text-[#e70054]'
                      : 'bg-[#dde2f2] text-[#404754]'
                  }`}
                >
                  {article.category}
                </span>
                <span className="text-[12px] text-[#404754]/70">
                  {article.source} • {article.timeAgo}
                </span>
              </div>

              <div className="flex items-start gap-3">
                <h3 className="text-[16px] font-semibold text-[#1A1C1F] group-hover:text-[#b90042] transition-colors line-clamp-2 flex-1 leading-snug">
                  {article.title}
                </h3>
                <img
                  className="w-14 h-14 rounded-xl object-cover flex-shrink-0 shadow-xs"
                  alt={article.imageAlt}
                  src={article.imageUrl}
                  referrerPolicy="no-referrer"
                />
              </div>
            </article>
          );
        })}
      </div>
    </section>
  );
};

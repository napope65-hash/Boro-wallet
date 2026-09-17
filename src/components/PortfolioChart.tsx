import React, { useState, useRef } from 'react';
import { Timeframe } from '../types';
import { TIMEFRAME_DATA } from '../data/mockData';

interface PortfolioChartProps {
  selectedTimeframe: Timeframe;
  onSelectTimeframe: (tf: Timeframe) => void;
  isBalanceHidden: boolean;
}

export const PortfolioChart: React.FC<PortfolioChartProps> = ({
  selectedTimeframe,
  onSelectTimeframe,
  isBalanceHidden,
}) => {
  const [hoverIndex, setHoverIndex] = useState<number | null>(null);
  const [hoverPoint, setHoverPoint] = useState<{ x: number; y: number; price: number } | null>(null);
  const svgRef = useRef<SVGSVGElement | null>(null);

  const currentData = TIMEFRAME_DATA[selectedTimeframe];
  const timeframes: Timeframe[] = ['1D', '1W', '1M', '1Y', 'ALL'];

  const handlePointerMove = (e: React.PointerEvent<SVGSVGElement>) => {
    if (!svgRef.current) return;
    const rect = svgRef.current.getBoundingClientRect();
    const clientX = e.clientX - rect.left;
    const normX = Math.max(0, Math.min(360, (clientX / rect.width) * 360));

    // Approximate height interpolation along the curve
    // Interpolate roughly between Low and High
    const range = currentData.high - currentData.low;
    const progress = normX / 360;
    // Y runs from 80 (bottom/low) to 10 (top/high)
    const curveY = 80 - Math.sin(progress * Math.PI * 0.85) * 60 - progress * 15;
    const clampedY = Math.max(5, Math.min(85, curveY));
    const price = currentData.low + ((85 - clampedY) / 80) * range;

    setHoverPoint({ x: normX, y: clampedY, price });
    setHoverIndex(Math.floor(progress * currentData.labels.length));
  };

  const handlePointerLeave = () => {
    setHoverPoint(null);
    setHoverIndex(null);
  };

  return (
    <div className="px-6 mt-4">
      {/* Timeframe Selector Pills */}
      <div className="flex items-center justify-between bg-[#F4F5F6] p-1 rounded-full" id="timeframeSelector">
        {timeframes.map((tf) => {
          const isActive = selectedTimeframe === tf;
          return (
            <button
              key={tf}
              onClick={() => onSelectTimeframe(tf)}
              className={`timeframe-btn flex-1 py-1.5 rounded-full text-[14px] transition-all text-center ${
                isActive
                  ? 'font-semibold bg-[#FDFCFB] shadow-xs text-[#e70054]'
                  : 'text-[#404754] hover:text-[#1A1C1F]'
              }`}
            >
              {tf}
            </button>
          );
        })}
      </div>

      {/* Interactive Portfolio Chart Card */}
      <div className="mt-4 relative w-full h-38 bg-gradient-to-b from-[#F4F5F6]/70 to-transparent rounded-2xl p-3 flex flex-col justify-between overflow-hidden border border-[#EDEDF2]/60">
        {/* High/Low Markers & Tooltip */}
        <div className="flex items-center justify-between text-[#404754] text-[11px] font-semibold tracking-wide z-10">
          <span className="bg-white/90 backdrop-blur-xs px-2.5 py-0.5 rounded-full shadow-xs border border-black/5">
            {isBalanceHidden
              ? 'High: $••,•••'
              : `High: $${currentData.high.toLocaleString('en-US', { minimumFractionDigits: 2 })}`}
          </span>

          {hoverPoint && !isBalanceHidden ? (
            <span className="bg-[#FF005E] text-white px-2.5 py-0.5 rounded-full shadow-sm font-semibold animate-fade-in">
              ${hoverPoint.price.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
            </span>
          ) : null}

          <span className="bg-white/90 backdrop-blur-xs px-2.5 py-0.5 rounded-full shadow-xs border border-black/5">
            {isBalanceHidden
              ? 'Low: $••,•••'
              : `Low: $${currentData.low.toLocaleString('en-US', { minimumFractionDigits: 2 })}`}
          </span>
        </div>

        {/* SVG Chart Line & Fill */}
        <div className="relative w-full h-24 my-1 cursor-crosshair">
          <svg
            ref={svgRef}
            className="w-full h-full overflow-visible touch-none"
            preserveAspectRatio="none"
            viewBox="0 0 360 90"
            onPointerMove={handlePointerMove}
            onPointerLeave={handlePointerLeave}
          >
            <defs>
              <linearGradient id="chartGrad" x1="0" x2="0" y1="0" y2="1">
                <stop offset="0%" stopColor="#FF005E" stopOpacity="0.22" />
                <stop offset="60%" stopColor="#FF005E" stopOpacity="0.05" />
                <stop offset="100%" stopColor="#FF005E" stopOpacity="0" />
              </linearGradient>
              <pattern
                id="dotPattern"
                patternUnits="userSpaceOnUse"
                width="12"
                height="12"
                x="0"
                y="0"
              >
                <circle cx="2" cy="2" r="1" fill="#FF005E" fillOpacity="0.12" />
              </pattern>
            </defs>

            {/* Pattern fill underneath */}
            <path
              d={currentData.areaPath}
              fill="url(#chartGrad)"
              className="transition-all duration-300 ease-out"
            />
            <path
              d={currentData.areaPath}
              fill="url(#dotPattern)"
              className="transition-all duration-300 ease-out"
            />

            {/* Main Trend Line */}
            <path
              d={currentData.path}
              fill="none"
              stroke="#FF005E"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2.5"
              className="transition-all duration-300 ease-out"
            />

            {/* Scrubber vertical line when hovering */}
            {hoverPoint ? (
              <g>
                <line
                  x1={hoverPoint.x}
                  y1={0}
                  x2={hoverPoint.x}
                  y2={90}
                  stroke="#FF005E"
                  strokeWidth="1.5"
                  strokeDasharray="3 3"
                  opacity="0.8"
                />
                <circle
                  cx={hoverPoint.x}
                  cy={hoverPoint.y}
                  r="5"
                  fill="#FF005E"
                  stroke="#FFFFFF"
                  strokeWidth="2"
                />
              </g>
            ) : (
              /* Current Endpoint Pulse */
              <g>
                <circle cx="360" cy="5" r="4" fill="#FF005E" />
                <circle
                  cx="360"
                  cy="5"
                  r="8"
                  fill="#FF005E"
                  fillOpacity="0.3"
                  className="animate-ping"
                />
              </g>
            )}
          </svg>
        </div>

        {/* Time labels footer */}
        <div className="flex items-center justify-between text-[#404754]/80 text-[10px] font-semibold tracking-wider">
          {currentData.labels.map((lbl, idx) => {
            const isLast = idx === currentData.labels.length - 1;
            return (
              <span
                key={idx}
                className={isLast ? 'text-[#e70054] font-bold' : ''}
              >
                {lbl}
              </span>
            );
          })}
        </div>
      </div>
    </div>
  );
};

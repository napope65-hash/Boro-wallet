# Boro Wallet - Android Application

A multi-asset wealth and digital asset operating system built natively for Android using Kotlin and Jetpack Compose.

## Features

- **Portfolio Management & Net Worth**: Live tracking of aggregated crypto, stocks, ETFs, and cash balances with hide/reveal visibility controls.
- **Real-Time Price Engine**: Live background price micro-fluctuations simulating active trading market hours with pause/resume toggle.
- **Interactive Canvas Bezier Chart**: Smooth cubic bezier spline chart supporting 1D, 1W, 1M, 1Y, and ALL timeframes with interactive scrub tooltip and gradient fill.
- **Trading (Buy & Sell)**: Full trade modal supporting USD amount inputs, percentage quick chips (25%, 50%, 75%, MAX), zero commissions, and real-time execution.
- **Transfers (Send & Receive)**: Instant cross-wallet transfers with recipient addressing, zero network fee protocol, and simulated QR-code receive screen.
- **Instant Cross-Asset Swaps**: Direct asset-to-asset zero-slippage swap interface with automatic rate calculation and invertible asset pairs.
- **Market Pulse & Benchmarks**: Realtime macro telemetry tracking S&P 500, Nasdaq 100, Bitcoin, and Gold alongside Fear & Greed sentiment index (74 Greed).
- **Market Intelligence**: Institutional newsfeed with category tags, reading time, and detailed analysis modal with bulleted key takeaways.
- **Boro Cash Account**: FDIC-insured cash balance management with instant deposit and withdrawal flows.
- **Biometric Security & Preferences**: Hardware passkey biometric toggle, push notification activity drawer, and rewards referral system.

## Architecture

- **Language**: Kotlin 2.1.10
- **UI Framework**: Jetpack Compose with Material Design 3 (M3)
- **Design System**: Edge-to-edge typography, custom color palette (`#B90042` Boro primary, `#FF005E` accent), and accessible touch targets.
- **Architecture**: MVVM with `StateFlow` and unidirectional data flow.

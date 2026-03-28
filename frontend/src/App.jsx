import { useEffect, useState } from 'react';
import { fetchMetrics, fetchSummary, fetchReliability, fetchSaturation } from './api';
import StatusCard from './components/StatusCard';
import SummaryBar from './components/SummaryBar';
import ReliabilityCard from './components/ReliabilityCard';
import SaturationCard from './components/SaturationCard';
import LatencyChart from './components/LatencyChart';
import MetricsTable from './components/MetricsTable';
import './App.css';
import PulsingRingSVG from "./components/PulsingRingSVG";

export default function App() {
    const [metrics,     setMetrics]     = useState([]);
    const [summary,     setSummary]     = useState(null);
    const [reliability, setReliability] = useState(null);
    const [saturation,  setSaturation]  = useState(null);
    const [backendUp,   setBackendUp]   = useState(true);

    const refresh = async () => {
        try {
            const [m, s, r, sat] = await Promise.all([
                fetchMetrics(),
                fetchSummary(),
                fetchReliability(),
                fetchSaturation()
            ]);
            setMetrics(m);
            setSummary(s);
            setReliability(r);
            setSaturation(sat);
            setBackendUp(true);
        } catch (err) {
            setBackendUp(false);
        }
    };

    useEffect(() => {
        refresh();
        const interval = setInterval(refresh, 10000);
        return () => clearInterval(interval);
    }, []);

    return (
        <div className="app">
            <header>
                <div className="header-left">
                    <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                        <PulsingRingSVG />
                        <h1>WatchTower</h1>
                    </div>
                    <span className="subtitle">Lightweight Observability System</span>
                </div>
                <div className="header-right">
                    <div className={`pulse-dot ${!backendUp ? 'offline' : ''}`} />
                    <span className="live-label">{backendUp ? 'Live' : 'Offline'}</span>
                </div>
            </header>

            {!backendUp && (
                <div className="offline-banner">
                    Backend is unreachable — retrying every 10 seconds
                </div>
            )}

            <div className="grid-top">
                <StatusCard metrics={metrics} />
                <SummaryBar summary={summary} />
                <ReliabilityCard reliability={reliability} />
            </div>

            <div className="grid-bottom">
                <LatencyChart metrics={metrics} />
                <SaturationCard saturation={saturation} />
            </div>

            <MetricsTable metrics={metrics} />
        </div>
    );
}
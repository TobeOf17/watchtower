export default function ReliabilityCard({ reliability }) {
    if (!reliability) return null;

    const fmt = (secs) => {
        if (!secs || secs === 0) return 'N/A';
        if (secs < 60) return `${secs}s`;
        return `${Math.floor(secs / 60)}m ${secs % 60}s`;
    };

    return (
        <div className="card">
            <h2>Reliability Indicators</h2>
            <div className="stat-row">
                <span>Avg MTTD</span>
                <strong>{fmt(reliability.avgMttdSeconds)}</strong>
            </div>
            <div className="stat-row">
                <span>Avg MTTR</span>
                <strong>{fmt(reliability.avgMttrSeconds)}</strong>
            </div>
            <div className="stat-row">
                <span>Total incidents</span>
                <strong>{reliability.totalIncidents}</strong>
            </div>
            {reliability.currentIncidentSeconds !== null && (
                <div className="stat-row">
                    <span>Current incident</span>
                    <strong className="error">{fmt(reliability.currentIncidentSeconds)} ongoing</strong>
                </div>
            )}
        </div>
    );
}
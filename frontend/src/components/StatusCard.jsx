export default function StatusCard({ metrics }) {
    const latest = metrics[0];
    if (!latest) return <div className="card">Waiting for data...</div>;

    const isUp = latest.availability === 'UP';

    return (
        <div className="card">
            <h2>Current Status</h2>
            <div className={`status-badge ${isUp ? 'up' : 'down'}`}>
                {isUp ? '● UP' : '● DOWN'}
            </div>
            <div className="stat-row">
                <span>Latest latency</span>
                <strong>{latest.latencyMs} ms</strong>
            </div>
            <div className="stat-row">
                <span>HTTP status</span>
                <strong>{latest.httpStatus || 'N/A'}</strong>
            </div>
            <div className="stat-row">
                <span>Last checked</span>
                <strong>{new Date(latest.timestamp).toLocaleTimeString()}</strong>
            </div>
        </div>
    );
}
export default function SaturationCard({ saturation }) {
    if (!saturation) return (
        <div className="card">
            <h2>Saturation</h2>
            <p style={{ color: 'var(--muted)', fontSize: 13 }}>Waiting for data...</p>
        </div>
    );

    const cpuLevel    = saturation.cpuPercent > 80 ? 'error'
        : saturation.cpuPercent > 50 ? 'warn' : 'ok';
    const memLevel    = saturation.memoryPercent > 80 ? 'error'
        : saturation.memoryPercent > 50 ? 'warn' : 'ok';

    return (
        <div className="card">
            <h2>Saturation</h2>

            <div className="stat-row">
                <span>CPU usage</span>
                <strong className={cpuLevel}>{saturation.cpuPercent}%</strong>
            </div>
            <div className="saturation-bar-bg">
                <div
                    className={`saturation-bar-fill ${cpuLevel}`}
                    style={{ width: `${Math.min(saturation.cpuPercent, 100)}%` }}
                />
            </div>

            <div className="stat-row" style={{ marginTop: 14 }}>
                <span>Memory usage</span>
                <strong className={memLevel}>{saturation.memoryPercent}%</strong>
            </div>
            <div className="saturation-bar-bg">
                <div
                    className={`saturation-bar-fill ${memLevel}`}
                    style={{ width: `${Math.min(saturation.memoryPercent, 100)}%` }}
                />
            </div>

            <div className="stat-row" style={{ marginTop: 14 }}>
                <span>Memory used</span>
                <strong>{saturation.memoryUsedMb} MB / {saturation.memoryTotalMb} MB</strong>
            </div>
        </div>
    );
}
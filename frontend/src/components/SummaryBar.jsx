export default function SummaryBar({ summary }) {
    if (!summary) return null;
    const rate = summary.errorRatePercent;
    const isHigh = rate > 10;

    return (
        <div className="card">
            <h2>Traffic Summary</h2>
            <div className="stat-row">
                <span>Total requests</span>
                <strong>{summary.totalRequests}</strong>
            </div>
            <div className="stat-row">
                <span>Failures</span>
                <strong>{summary.failureCount}</strong>
            </div>
            <div className="stat-row">
                <span>Error rate</span>
                <strong className={isHigh ? 'error' : 'ok'}>{rate}%</strong>
            </div>
            <div className="error-rate-bar-wrap">
                <div className="error-rate-bar-bg">
                    <div
                        className={`error-rate-bar-fill ${isHigh ? 'high' : ''}`}
                        style={{ width: `${Math.min(rate, 100)}%` }}
                    />
                </div>
            </div>
        </div>
    );
}
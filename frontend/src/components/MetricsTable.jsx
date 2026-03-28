export default function MetricsTable({ metrics }) {
    return (
        <div className="card">
            <h2>Recent Polls</h2>
            <table>
                <thead>
                <tr>
                    <th>Time</th>
                    <th>Latency</th>
                    <th>Status</th>
                    <th>HTTP</th>
                    <th>Availability</th>
                </tr>
                </thead>
                <tbody>
                {metrics.map(m => (
                    <tr key={m.id}>
                        <td>{new Date(m.timestamp).toLocaleTimeString()}</td>
                        <td>{m.latencyMs} ms</td>
                        <td className={m.isSuccess ? 'ok' : 'error'}>
                            {m.isSuccess ? 'Success' : 'Failed'}
                        </td>
                        <td>{m.httpStatus || 'N/A'}</td>
                        <td className={m.availability === 'UP' ? 'ok' : 'error'}>
                            {m.availability}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
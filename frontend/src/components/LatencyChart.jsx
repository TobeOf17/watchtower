import {
    LineChart, Line, XAxis, YAxis, CartesianGrid,
    Tooltip, ResponsiveContainer, ReferenceLine
} from 'recharts';

export default function LatencyChart({ metrics }) {
    const data = [...metrics].reverse().map(m => ({
        time: new Date(m.timestamp).toLocaleTimeString(),
        latency: m.latencyMs,
    }));

    return (
        <div className="card">
            <h2>Latency Over Time (ms)</h2>
            <ResponsiveContainer width="100%" height={260}>
                <LineChart data={data}>
                    <CartesianGrid strokeDasharray="3 3" stroke="#e4ebe9" />
                    <XAxis dataKey="time" tick={{ fontSize: 11, fill: '#6b7b7a' }} interval="preserveStartEnd" />
                    <YAxis tick={{ fontSize: 11, fill: '#6b7b7a' }} />
                    <Tooltip
                        contentStyle={{
                            background: 'white',
                            border: '1px solid #e4ebe9',
                            borderRadius: '8px',
                            fontSize: '13px'
                        }}
                        formatter={(val) => [`${val} ms`, 'Latency']}
                    />
                    <ReferenceLine
                        y={1000}
                        stroke="#d4a017"
                        strokeDasharray="4 4"
                        label={{ value: 'Slow threshold', fontSize: 11, fill: '#a07800' }}
                    />
                    <Line
                        type="monotone"
                        dataKey="latency"
                        stroke="#005b52"
                        strokeWidth={2.5}
                        dot={false}
                        activeDot={{ r: 4, fill: '#9fc031' }}
                    />
                </LineChart>
            </ResponsiveContainer>
        </div>
    );
}
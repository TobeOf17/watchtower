export default function PulsingRingSVG() {
    return (
        <svg width="36" height="36" viewBox="0 0 40 40" style={{ flexShrink: 0 }}>
            <circle cx="20" cy="20" r="3" fill="#005b52"/>
            <circle cx="20" cy="20" r="10" fill="none" stroke="#9fc031" strokeWidth="1.5" opacity="0.6"/>
            <circle cx="20" cy="20" r="3" fill="none" stroke="#005b52" strokeWidth="1.5">
                <animate attributeName="r" values="3;18;3" dur="2s" repeatCount="indefinite"/>
                <animate attributeName="opacity" values="0.9;0;0.9" dur="2s" repeatCount="indefinite"/>
            </circle>
            <circle cx="20" cy="20" r="3" fill="none" stroke="#9fc031" strokeWidth="1">
                <animate attributeName="r" values="3;18;3" dur="2s" begin="0.6s" repeatCount="indefinite"/>
                <animate attributeName="opacity" values="0.7;0;0.7" dur="2s" begin="0.6s" repeatCount="indefinite"/>
            </circle>
        </svg>
    );
}
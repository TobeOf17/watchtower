import axios from 'axios';

const BASE = 'http://localhost:8080/api';

export const fetchMetrics     = () => axios.get(`${BASE}/metrics`).then(r => r.data);
export const fetchSummary     = () => axios.get(`${BASE}/summary`).then(r => r.data);
export const fetchReliability = () => axios.get(`${BASE}/reliability`).then(r => r.data);
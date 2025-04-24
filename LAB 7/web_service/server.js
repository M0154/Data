const express = require('express');
const app = express();
const PORT = 3000;

app.use(express.json());

app.get('/status', (req, res) => {
    res.json({ message: 'Service is live!', timestamp: new Date() });
});

app.post('/compute', (req, res) => {
    const { data } = req.body;
    const result = data.reduce((a, b) => a + b, 0);
    res.json({ result, processedAt: new Date() });
});

app.listen(PORT, () => {
    console.log(`✅ Web service running at http://localhost:${PORT}`);
});

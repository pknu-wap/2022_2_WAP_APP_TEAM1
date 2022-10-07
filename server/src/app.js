const express = require('express');
const app = express();
const port = 3000;
const db = require("./config-db");
const router = require("./routes");

app.use('/', router);
app.listen(port, async () => {
    console.log(`http://localhost:${port}`)
})
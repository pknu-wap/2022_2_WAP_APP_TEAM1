const express = require('express');
const app = express();
const port = 3000;
const db = require("./util/database");
const router = require("./routes");

app.use('/', router);
app.listen(port, async () => {
    require("dotenv").config();

    try {
        db.authenticate();
    }
    catch (err) {
        console.log('DB 연결 중 오류 발생: ', err);
        process.exit();
    }
    console.log(`http://localhost:${port}`)
})
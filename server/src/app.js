require("dotenv").config({ path: "./src/.env" });
const express = require('express');
const app = express();
const port = 3000;
const db = require("./util/database");
const api = require('./routers');

app.use(express.json()); //application/json 타입 사용 등록
app.use(express.urlencoded({extended: true})); //application/x-www-form-urlencoded 타입 사용 등록

app.use("/api", api);

app.listen(port, async () => {
    console.log(process.env.DB_HOST);
    console.log(process.env.AMADEUS_API_KEY);
    try {
        db.authenticate();
    }
    catch (err) {
        console.log('DB 연결 중 오류 발생: ', err);
        process.exit();
    }
    try
    {
        const kiwi_api = require('./kiwi-api');
        let kiwi = new kiwi_api();
        await kiwi.findFlight('PUS', 'GMP', '2022-11-03', '2022-11-03', '', '', 'oneway', 1, 0, 10);
    }
    catch(err)
    {
        console.log('DB 연결 중 오류 발생: ', err);
        process.exit();
    }
    console.log(`http://localhost:${port}`)
})
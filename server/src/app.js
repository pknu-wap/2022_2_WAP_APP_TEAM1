require("dotenv").config({ path: "./src/.env" });
const express = require('express');
const app = express();
const port = 3000;
const api = require('./routers');
const models = require('./models');

app.use(express.json()); //application/json 타입 사용 등록
app.use(express.urlencoded({ extended: true })); //application/x-www-form-urlencoded 타입 사용 등록
app.use(function (error, req, res, next) {
    console.error(error);
    return res.status(500).send('Internal Server Error');
    });
app.use("/api", api);

app.listen(port, async () => {
    try {
        await models.sequelize.sync({ force: false, alter: false })
    }
    catch (err) {
        console.log('DB 연결 중 오류 발생: ', err);
        process.exit();
    }
    try
    {
        const flightData = require('./flight');
        let flight = new flightData();
        await flight.getInfoByFlightNumber('2022-11-24', '7C', '211');
        //await kiwi.findFlight('CJU', 'GMP', '2022-11-16', '2022-11-19', '', 1, 0, 1);
    }
    catch(err)
    {
        console.log('항공권 API 테스트 중 오류 발생: ', err);
        process.exit();
    }

    console.log(`http://localhost:${port}`);
    console.log('[Server] Server is running...');
})
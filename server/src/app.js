require("dotenv").config();
const express = require('express');
const app = express();
const port = 3000;
const db = require("./util/database");
const api = require('./routers');
const swaggerFile = require('./swagger_output.json');
const swaggerUi = require('swagger-ui-express');

app.use(express.json()); //application/json 타입 사용 등록
app.use(express.urlencoded({extended: true})); //application/x-www-form-urlencoded 타입 사용 등록

app.use("/api", api);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerFile));

app.listen(port, async () => {
    try {
        db.authenticate();
    }
    catch (err) {
        console.log('DB 연결 중 오류 발생: ', err);
        process.exit();
    }
    console.log(`http://localhost:${port}`)
})
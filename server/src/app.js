require("dotenv").config({path: "./src/.env"});
const app = require('express')();
const port = 3000;
const api = require('./routers');
const models = require('./models');
const bodyParser = require('body-parser');
const {Server} = require('socket.io');
const io = new Server();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false})); //application/x-www-form-urlencoded 타입 사용 등록
app.use("/api", api);

app.listen(port, async () => {
    try {
        await models.sequelize.sync({force: false, alter: false})
    } catch (err) {
        console.log('DB 연결 중 오류 발생: ', err);
        process.exit();
    }
    console.log(`http://localhost:${port}`);
    console.log('[Server] Server is running...');
})
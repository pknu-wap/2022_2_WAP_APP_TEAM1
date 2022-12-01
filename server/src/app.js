require("dotenv").config({path: "./src/.env"});
const app = require('express')();
const port = 3000;
const api = require('./routers');
const models = require('./models');
const bodyParser = require('body-parser');
const socketport = 8080;
const { Server } = require('socket.io');
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
    io.listen(socketport);
    console.log(`socket.io listening on port ${socketport}`);
    io.on('connection', async (socket) => {
        socket.on('leave', async (data) => {
            console.log('leave', data);
            socket.leave(socket.TripId);
        });
        socket.on('join', async (data) => {
            const {TripId, UserId} = data;
            socket.TripId = TripId;
            socket.UserId = UserId;

            if (TripId == undefined || UserId == undefined) {
                console.log("connection: TripId or UserId is undefined");
                return;
            }
            try {
                let data = await models.User.findByPk(UserId);
                socket.Nickname = data.Nickname;
            } catch (err) {
                console.log("connection: User not found, UserId: ", UserId);
                console.log('connection error: ', err.message);
                console.log('connection error: ', err.stack);
                return;
            }
            socket.join(TripId);
            console.log("join in " + TripId);
            console.log(socket.rooms)
            console.log("UserId: " +UserId+ " connected to " + TripId);
            //이전 채팅 내역 가져오기(TripId)
            let result = [];//새로 읽은 내역을 담는 객체
            let chatList = await models.Chat.findAll({ //TripId에 해당하는 모든 채팅 내역 가져오기
                where: {
                    TripId: TripId
                }
            });
            for (let i = 0; i < chatList.length; i++) { //모든 채팅 내역에 대해
                let writterNickname = '';
                try {
                    let data = await models.User.findByPk(chatList[i].UserId);
                    writterNickname = data.Nickname;
                } catch (err) {
                    console.log("connection: User not found, UserId: ", UserId);
                    console.log('connection error: ', err.message);
                    console.log('connection error: ', err.stack);
                    return;
                }
                chatList[i].dataValues.Nickname = writterNickname;
                chatList[i].dataValues.isWrittenByMe = (chatList[i].UserId == UserId);
                result.push(chatList[i].dataValues);
            }
            socket.emit('evtConnection', result);
        });

        //채팅 보내기(TripId,UserId, Content)
        socket.on("addMessage", async (receivedData) => {
            if (socket.TripId == undefined || socket.UserId == undefined) {
                console.log("addMessage: TripId or UserId is undefined");
                return;
            }
            let transaction = await models.sequelize.transaction();
            try {
                const {Content} = receivedData;
                if (Content == undefined) {
                    console.log("addMessage: Content를 찾을 수 없습니다.");
                    console.log(receivedData);
                } else {
                    let chat = await models.Chat.create({ //채팅 내역 추가
                        TripId: socket.TripId,
                        UserId: socket.UserId,
                        Content: Content,
                        Type: 0
                    }, {transaction: transaction});

                    if (chat == null) {
                        await transaction.rollback();
                        console.log('chat is null');
                    }
                    else {
                        await transaction.commit();
                        chat.dataValues.Nickname = socket.Nickname;
                        io.sockets.in(socket.TripId).emit("evtMessage", chat.dataValues); //나를 제외한 방 인원에게 이벤트 전송
                    }
                    console.log("success to send Message : ", chat.dataValues);
                }
            } catch (e) {
                //await transaction.rollback();
                console.log('errors: ', e.message);
                console.log('stack: ', e.stack);
            }
        })

        //파일 보내기(TripId,UserId, File)
        socket.on("addFile", async (receivedData) => {
            const { FileName } = receivedData;
            if (FileName == undefined) {
                console.log("addFile: FileName을 찾을 수 없습니다.");
                console.log(receivedData);
                return;
            }
            let transaction = await models.sequelize.transaction();
            try {
                let chat = await models.Chat.create({ //채팅 내역 추가
                    TripId: TripId,
                    UserId: UserId,
                    Content: FileName,
                    Type: 1
                }, {transaction: transaction});

                if (chat == null) {
                    await transaction.rollback();
                    console.log('chat is null');
                }
                else {
                    await transaction.commit();
                    chat.dataValues.Nickname = Nickname;
                    socket.broadcast.to(TripId).emit("evtMessage", chat.dataValues); //나를 제외한 방 인원에게 이벤트 전송
                }
                console.log("success to send File : ${chat}", chat.dataValues);
            } catch (e) {
                await transaction.rollback();
                console.log('errors: ', e.message);
                console.log('stack: ', e.stack);
            }
        });
        //채팅방 나가기
        socket.on("disconnect", () => {
            console.log("leave from room");// connection 때 선언한 TripId
        });
    });

    console.log(`http://localhost:${port}`);
    console.log('[Server] Server is running...');
})
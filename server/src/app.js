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
        const {TripId}  = socket.handshake.query;
        const { UserId } = socket.handshake.query;
        console.log('TripId: ' + TripId);
        console.log('UserId: ' + UserId);
        if (TripId == undefined || UserId == undefined) {
            console.log("Bad Request");
        }
        socket.join(TripId);
        console.log("join in" + TripId);
        console.log(socket.rooms)
        console.log("UserId: " +UserId+ " connected to " + TripId);
        //이전 채팅 내역 가져오기(TripId)
        let result = [];//새로 읽은 내역을 담는 객체
        let chatList = await models.Chat.findAll({ //TripId에 해당하는 모든 채팅 내역 가져오기
            where: {
                TripId: TripId
            }
        });
        console.log(chatList[0].dataValues);
        for (let i = 0; i < chatList.length; i++) { //모든 채팅 내역에 대해
            let readChat = await models.ChatRead.findAll({ //읽은 채팅 내역 가져오기
                where: {
                    TripId: TripId,
                    ChatId: chatList[i].ChatId,
                    UserId: UserId
                }, raw: true,
            });
            result.push(readChat);
            if (readChat.length == 0) { //읽은 채팅 내역이 없으면
                let chat = {};//안 읽은 채팅 내역을 담는 객체
                chat.ChatId = chatList[i].ChatId;
                chat.UserId = chatList[i].UserId;
                chat.Content = chatList[i].Content;
                chat.CreatedAt = chatList[i].CreatedAt;
                await models.ChatRead.create({//읽은 채팅에 내역 추가
                    TripId: TripId,
                    ChatId: chatList[i].ChatId,
                    UserId: UserId
                });
                result.push(chat);
            }
        }
        console.log(result);
        socket.to(TripId).emit('connection', result);
        //채팅 보내기(TripId,UserId, Content)
        socket.on("addMessage", async (socket) => {
            //const { TripId } = socket;
            //const { UserId } = socket;
            let transaction = await models.sequelize.transaction();
            try {
                const {Content} = socket;
                console.log(TripId, UserId, Content);
                if (Content == undefined) {
                    console.log("Bad Request");
                }
                else
                {
                    let chat = await models.Chat.create({ //채팅 내역 추가
                        TripId: TripId,
                        UserId: UserId,
                        Content: Content
                    }, {transaction: transaction});

                    if (chat == null) {
                        await transaction.rollback();
                        console.log('chat is null');
                    }
                    else {
                        await transaction.commit();
                        io.to(TripId).emit("addMessage", chat);
                    }
                    console.log("success to send Message : ${chat}", chat);
                }
            } catch (e) {
                await transaction.rollback();
                console.log('error: ', e.message);
                console.log('stack: ', e.stack);
            }
        })
        //채팅방 나가기
        socket.on("disconnect", () => {
            console.log("leave from" + TripId);
        })
    });

    console.log(`http://localhost:${port}`);
    console.log('[Server] Server is running...');
})
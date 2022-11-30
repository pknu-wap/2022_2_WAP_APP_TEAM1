require("dotenv").config({ path: "./src/.env" });
const express = require('express');
const app = express();
const port = 3000;
const api = require('./routers');
const models = require('./models');
const { raw2str } = require('./util/rawtostr');
const socketport = 80;
const { Server } = require('socket.io');
const { query } = require("express");
const io = new Server();
//const chat=require('./socket');

app.use(express.json()); //application/json 타입 사용 등록
app.use(express.urlencoded({ extended: true })); //application/x-www-form-urlencoded 타입 사용 등록
app.use(function (error, req, res, next) {
    console.error(error);
    return res.status(500).send('Internal Server Error');
});
app.use("/api", api);


app.listen(port, async () => {
    io.listen(8080);
    console.log("socket.io listening on port 8080");
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
        console.log("connected to " + TripId, UserId);
        //이전 채팅 내역 가져오기(TripId)
        let result = {};//새로 읽은 내역을 담는 객체
        let chatList = raw2str(await models.Chat.findAll({ //TripId에 해당하는 모든 채팅 내역 가져오기
            where: {
                TripId: TripId
            }
        }));
        console.log(TripId+ 'chatlist: '+chatList);
        for (let i = 0; i < chatList.length; i++) { //모든 채팅 내역에 대해
            let readChat = await models.ChatRead.findAll({ //읽은 채팅 내역 가져오기
                where: {
                    TripId: TripId,
                    ChatId: chatList[i].ChatId,
                    UserId: UserId
                }
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
        console.log('result: ' +result);
        socket.to(TripId).emit('connection', result);
        //채팅 보내기(TripId,UserId, Content)
        socket.on("addMessage", async (socket) => {
            //const { TripId } = socket;
            //const { UserId } = socket;
            const {Content} = socket;
            console.log(TripId, UserId, Content);
            if (Content == undefined) {
                console.log("Bad Request");
            }
            let chat = raw2str(await models.Chat.create({
                TripId: TripId,
                UserId: UserId,
                Content: Content
            }));
            if (chat == null) {
                return res.send({ status: false, reason: "채팅 전송 도중 오류가 발생했습니다." });
            }
            else {
                io.to(TripId).emit("addMessage", chat);
            }
            console.log("success to send Message : ${chat}", chat);
        })
        //채팅방 나가기
        socket.on("disconnect", () => {
            console.log("leave from" + TripId);
        })
    });
    /*
    server.listen(port, async () => {
        console.log(`http://localhost:80`);
        console.log('[Socket] is running...');
    });
    */

    try {
        await models.sequelize.sync({ force: false, alter: false })
    }
    catch (err) {
        console.log('DB 연결 중 오류 발생: ', err);
        process.exit();
    }
    /*try
    {
        const kiwi_api = require('./kiwi-api');
        let kiwi = new kiwi_api();
        await kiwi.findFlight('PUS', 'GMP', '2022-11-03', '2022-11-03', '', '', 'oneway', 1, 0, 10);
    }
    catch(err)
    {
        console.log('DB 연결 중 오류 발생: ', err);
        process.exit();
    }*/

    console.log(`http://localhost:${port}`);
    console.log('[Server] Server is running...');
})
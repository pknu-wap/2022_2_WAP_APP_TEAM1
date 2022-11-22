const chatRouter=require('express').Router();
const chatService=require('../../controller/chat');
const token=require('../../util/jwt');
const { chatMiddleware } = require('../../controller/chat/middleware')
const socketIO=require('socket.io')
const io=socketIO(server);
chatRouter.use(token.authenticateAccessToken);

function wrapAsync(fn) {
    return function (req, res, next) {
        fn(req, res, next).catch(next);
    };
}

// Chatting
/*
get:채팅내역 불러오기
*/
chatRouter.put('/trips/:TripId/chat',io,chatMiddleware,wrapAsync(chatting.createChat));
chatRouter.get("/trips/:TripId/chat",io,wrapAsync(chatService.getChat));


//Chatting Participants
/*
put:채팅방 만들기
delete:채팅방 tkrwpgkr;
*/
chatRouter.put("/trips/:TripId/chat",chatMiddleware,wrapAsync(chatService.createRoom));
chatRouter.delete("/trips/:TripId/chat/:ChatId",wrapAsync(chatService.deleteRoom));

module.exports = chatRouter;
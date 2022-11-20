const chatRouter=require('express').Router();
const chatService=require('../../controller/chat');
const token=require('../../util/jwt');
const { chatMiddleware } = require('../../controller/chat/middleware')
const chatting=require("../../controller/chat/chatting");
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
chatRouter.get("/:TripId/chat/:Tripid",wrapAsync(chatService.getChat));

//Chatting Participants
/*
put:채팅방 참여
get:채팅방 불러오기
delete:채팅방 나가기...가 필요해? 
*/
chatRouter.put("/:TripId/chatroom",chatMiddleware,wrapAsync(chatService.createChatRoom));
chatRouter.get("/:TripId/chatroom",chatMiddleware,wrapAsync(chatService.getChatRoom));
chatRouter.delete("/:TripId/chatroom",wrapAsync(chatService.deleteChatRoom));

module.exports = chatRouter;
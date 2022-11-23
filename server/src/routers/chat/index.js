const chatRouter=require('express');
const chatService=require('../../controller/chat');
const chatting = require('../../controller/chat/chatting');
const token=require('../../util/jwt');
//const io=require('socket.io')(server);
//const chatting=require('../../controller/chatting')(io);
//chatting.start(io);
chatRouter.use(token.authenticateAccessToken);

function wrapAsync(fn) {
    return function (req, res, next) {
        fn(req, res, next).catch(next);
    };
}

chatRouter.put('/trips/:TripId/chat',wrapAsync(chatService.sendChat));
chatRouter.get("/trips/:TripId/chat",wrapAsync(chatService.getUnreadChat));
chatRouter.delete('/trips/:TripId/chat',wrapAsync(chatService.deleteChat));
chatRouter.delete("/trips/:TripId/chat/:ChatId",wrapAsync(chatService.deleteRoom));

//chatRouter.put("/trips/:TripId/chat",chatMiddleware,wrapAsync(chatService.createRoom));

module.exports = chatRouter,chatting;
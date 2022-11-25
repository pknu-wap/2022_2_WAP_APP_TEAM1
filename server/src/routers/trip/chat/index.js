const chatRouter=require('express').Router();
const chatService=require('../../../controller/chat');

function wrapAsync(fn) {
    return function (req, res, next) {
        fn(req, res, next);
    };
}
// localhost:3000/api/trip/:TripId/chat/trip/:TripId/chat


chatRouter.post('/',wrapAsync(chatService.sendMessage));
chatRouter.get("/",wrapAsync(chatService.getUnreadChat));
//chatRouter.delete('/',wrapAsync(chatService.deleteChat));


module.exports = chatRouter;
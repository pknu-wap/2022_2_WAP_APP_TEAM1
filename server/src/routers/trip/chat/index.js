//const chatRouter=require('express');
const chatRouter= require('../../../socket').Router();

// localhost:3000/api/trip/:TripId/chat/trip/:TripId/chat

//chatRouter.route('/',chatService);
//chatRouter.get("/",wrapAsync(chatService.getUnreadChat));
//chatRouter.delete('/',wrapAsync(chatService.deleteChat));


module.exports = chatRouter;
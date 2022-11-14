/*채팅방에 채팅을 보내고 받는 라우터*/
const express = require('express');
const router = express.Router();
const { Chat } = require('../../models');
const socketio=require('socket.io');
const { connectionClass } = require('oracledb');
const { Model } = require('sequelize-oracle');
const { REPL_MODE_SLOPPY } = require('repl');

var io=socketio();


io.on('connection',function(socket){//connection이라는 이벤트가 발생하면 콜백함수 실행
	socket.on('join',function(data){//join이라는 이벤트가 발생하면 콜백함수 실행
		socket.join(data.room);
	});
	socket.on('message',function(data){
		io.to(data.room).emit('new message',{user:data.user,message:data.message});
		let data= models.Chat.create({
			UserId:req.token.userId,
			RoomId:req.params.RoomId,
			Chat:data.message
		})
	});
	socket.on('disconnect',function(data){
		if(!data){
			return;
		}
		socket.leave(data.room);
	});
});

		


/*채팅방에 채팅을 보내고 받는 라우터*/
const express = require('express');
const socketio=require('socket.io');

var io=socketio();


io.on('connection',function(socket){//connection이라는 이벤트가 발생하면 콜백함수 실행
	socket.on('join',function(data){//join이라는 이벤트가 발생하면 콜백함수 실행
		socket.join(data.room);
	});
	socket.on('message',function(data){//message라는 이벤트가 발생하면 콜백함수 실행
		io.to(data.room).emit('message',data.msg);
	});
	socket.on('disconnect',function(data){//disconnect라는 이벤트가 발생하면 콜백함수 실행
		if(!data){
			return;
		}
		socket.leave(data.room);
	});
});

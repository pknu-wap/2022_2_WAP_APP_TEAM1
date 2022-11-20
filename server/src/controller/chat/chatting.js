/*채팅방에 채팅을 보내고 받는 라우터*/
const express = require('express');
const socketio=require('socket.io');
const app=express();

app.use(function(req,res)){
      res.io=io;
      next();
}

const io=socketio();
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

const userIo=io.of("/user");
userIo.on("connection",(socket)=>{
    console.log("connected to user namesapaace"+socket.username)
})
userIo.use((socket,next)=>{
    if (socket.handshake.auth.token){
        socket.username=getUsernameFromToken(socket.handshake.auth.token);
        next()
    }else{
        next(new Error("invalid token"))
    }
})
function getUsernameFromToken(token){
    return token;
}
io.on("connection",(socket)=>{
    console.log(socket.id)
    socket.on("send-message",(message,room)=>{
    if (room===""){
        socket.broadcast.emit("receive-message",message)
    }else{
        socket.to(room).emit("receive-message",message)
    }
})
    socket.on("join-room",(room,cb)=>{
        socket.join(room)
        cb(`joined ${room}`)
    })
    socket.on("leave-room",(room,cb)=>{
         socket.leave(room)
         cb(`left ${room}`)
      })
})

instrument(io, {
    auth: false
})
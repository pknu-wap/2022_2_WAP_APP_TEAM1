/*
const express = require('express');
const app=express();
const io=require('socket.io');

module.exports = {
    start:function(io){
        io.on('connection',function(socket){//클라이언트가 접속하면 connection이벤트 발생
            console.log('user connected');
            socket.on('join',function(TripId){//클라이언트가 join이벤트를 보내면
                socket.join(TripId.room)//TripId.room에 해당하는 방에 join
                /*try{
                    const getRoom= chatService.getRoom(TripId);//채팅내역을 불러옴
                    io.to(TripId.room).emit('join',getRoom);//채팅내역을 클라이언트에게 보냄
                }
                catch(err){
                    console.log(err);
                }
                socket.on('chat',function(chat){//클라이언트가 chat이벤트를 보내면
                    /*try {//채팅을 저장하고
                        const chat = chatService.createChat(UserId, message, Time, Read);
                    }
                    catch (err) {
                        console.log('DB 연결 중 오류 발생: ', err);
                    }
                    socket.to(TripId).emit('chat',chat);//TripId.room에 해당하는 방에 있는 클라이언트에게 chat이벤트를 보냄
                });

            })
        })
        io.on('disconnect',()=>{
                console.log('user disconnected');
        })
    }
}
*/
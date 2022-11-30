"use strict"
const app=require('express')();
const server= require('http').createServer(app);
const io= require("socket.io")(server);
const models=require('../../../server/src/models');
//const wsModule=require('ws');
//const sequelize=require('socket.io-sequelize');
const {raw2str} = require("../../../server/src/util/rawtostr");

/*
const webSocketServer=new wsModule.Server(
    {
        server:server,
    }
);
*/
//const WebSocketServer=require('websocket').server;
//const port=80;
//const {instrument} = require('@socket.io/admin-ui');

//연결, 채팅방 있으면 join, 없으면 create(TripId, UserId)
io.on('connection', async (socket)=>{
    const {TripId}  = socket.query;
        const { UserId } = socket.query;
        console.log('TripId: ' + TripId);
        console.log('UserId: ' + UserId);
        if (TripId == undefined || UserId == undefined) {
            console.log("Bad Request");
        }
        socket.join(TripId);
        console.log("join in" + TripId);
        console.log("connected to " + TripId, UserId);

    //이전 채팅 내역 가져오기(TripId)
    let result={};//새로 읽은 내역을 담는 객체
    let chatList=raw2str(await models.Chat.findAll({ //TripId에 해당하는 모든 채팅 내역 가져오기
        where:{
            TripId:TripId
        }
    }));
    for(let i=0;i<chatList.length;i++){ //모든 채팅 내역에 대해
        let readChat=await models.ChatRead.findAll({ //읽은 채팅 내역 가져오기
            where:{
                TripId:TripId,
                ChatId:chatList[i].ChatId,
                UserId:UserId
            }
        });
        result.push(readChat);
        if(readChat.length==0){ //읽은 채팅 내역이 없으면
            let chat={};//안 읽은 채팅 내역을 담는 객체
            chat.ChatId=chatList[i].ChatId;
            chat.UserId=chatList[i].UserId;
            chat.Content=chatList[i].Content;
            chat.CreatedAt=chatList[i].CreatedAt;
            await models.ChatRead.create({//읽은 채팅에 내역 추가
                TripId:TripId,
                ChatId:chatList[i].ChatId,
                UserId:UserId
            });
            result.push(chat);
            console.log(chat);
        }
    }
    socket.to(TripId).emit('getChat',result);

    //채팅 보내기(TripId,UserId, ChatId, Content)
    socket.on("addMessage",async (socket)=>{
        const {Content}=socket;
        console.log("ChatId, Content",ChatId,Content);
        if(Content==undefined){
            return res.send({status:false,reason:"Bad Request"});
        }
        let chat=raw2str(await models.Chat.create({
            TripId:TripId,
            UserId:UserId,
            Content:Content
        }));
        console.log(Content)
        if(chat==null){
            return res.send({status:false,reason:"채팅 전송 도중 오류가 발생했습니다."});
        }
        else{
            io.to(TripId).emit("sendMessage",chat);
        }
        console.log("success to send Message : ${chat}",chat);
    }),
    //채팅방 나가기
    socket.on("disconnect",()=>{
        console.log("leave from"+room);
    })
});
server.listen(port, async () => {
    console.log(`http://localhost:${port}`);
    console.log('[Socket] is running...');
});
/*
instrument(io,{
    auth: {
        type: "basic",
        username: "admin",
        password: "admin"
    },
})
*/
/*

let activeUsers={};

io.on('connection', function(socket){
    console.log('socket connected');
    socket.on('newUser',(User)=>{
        const {TripId,name}=User
        activeUsers[TripId]=name;
        console.log(name+'님이 접속하였습니다.');
        console.log(activeUsers);
        });
    socket.on('disconnect', function() {
        console.log('user disconnected');
    });
    socket.on('sendMessage', (msg)=>{
        const {TripId,name,message}=msg;
        console.log(TripId+name+'님이 메시지를 보냈습니다.');
        console.log('message: ' + message);
        io.to(TripId).emit('message',message);
      });
});

server.listen(port, async () => {
    console.log(`http://localhost:${port}`);
    console.log('[Socket] is running...');
    
})
*/


// Compare this snippet from server\src\socket\controller.js:
/*
    socket.on('getChat', async ()=>{
        socket.emit('chatHistoric', await controller.getChat()) 
    }),
    socket.on('newMessage', obMessage=>{
        //db에 저장
        controller.saveMessage(obMessage);
        //모든 연결에 전송
        io.sockets.emit('newMessage', [obMessage])
    })
});
*/






/*
export const initWebSocket = (io) =>{

    //sockets Productos
    socketProducts(io);

    //Sockets Chat
    socketChat(io);

    //Envío usuario:
    socketLoginUser(io);

}
*/


/*
        //const chat =io.of('/chat'); //app.set('io',io); createRoom, joinRoom, leaveRoom, sendMessage, receiveMessage, deleteMessage, getUnreadMessage
        app.set('io',io);
        io.on('connection',(socket)=>{
            console.log('socket connected');
            TripId=socket.handshake.query.TripId;//소켓 커넥션시 쿼리스트링으로 트립아이디 받아옴
            UserId=socket.handshake.query.UserId;
            socket.join(TripId)={
                TripId:TripId,
                UserId:UserId,
            };
            socket.on('getChat', async ()=>{
                socket.emit('chatHistoric', await controller.getUnreadMessage()) 
            })
                socket.on('joinRoom',(TripId)=>{
                    socket.join(TripId);
                    console.log('joinRoom'+TripId);
                    });
                socket.on('disconnect',()=>{
                    console.log('chat 네임스페이스 접속 해제');
                    socket.leave(TripId);
                    });
                socket.on('sendMessage',(TripId,Content)=>{
                    socket.to(TripId).emit('sendMessage',{
                        user:'user',
                        Content,
                    });
                });
            socket.on('')
                socket.on('getUnreadMessage',(TripId,content)=>{
                    socket.to(TripId).emit('getUnreadMessage',{
                        user:'user',
                        content,
                    });
                });
            });
        server.listen(3000,()=>{
            console.log('server is running');
        });

        //const req=socket.request;
        
        socket.on('joinRoom',(TripId)=>{
            req=TripId;
            socket.join(TripId);
            console.log('joinRoom',TripId);
            });
        socket.on('disconnect',()=>{
            console.log('chat 네임스페이스 접속 해제');
            socket.leave(TripId);
            });
        socket.on('sendMessage',(TripId,Content)=>{
            socket.to(TripId).emit('sendMessage',{
                user:'user',
                Content,
            });
        });
        socket.on('getUnreadMessage',(TripId,content)=>{
            socket.to(TripId).emit('getUnreadMessage',{
                user:'user',
                content,
            });
        });
        
    */

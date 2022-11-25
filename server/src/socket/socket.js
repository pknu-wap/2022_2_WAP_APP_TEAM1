const app=require('express')();
const server= require('http').createServer(app);
const io=require('socket.io')(server);
const port=80;

io.on('connection', function(socket){
    console.log('socket connected');
    socket.on('newUser',(name)=>{
        console.log(name+'님이 접속하였습니다.');
        });
    socket.on('disconnect', function() {
        console.log('user disconnected');
    });
    socket.on('sendMessage', function(msg){
        console.log('message: ' + msg);
      });
});

server.listen(port, async () => {
    console.log(`http://localhost:${port}`);
    console.log('[Socket] is running...');
    
})


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

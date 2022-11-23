'use strict';
const models=require("../../models")
const {raw2str}=require("../../util/rawtostr")

module.exports={

   
    /*
    //get(GET)
    async getChat(req,res){
        if (req.body.TripId===undefined){
            return res.send({status:false, reason: "Bad Request"});
        }
        let isMember=await models.Trip.isMemberOf(models,trip.TripId,UserId)//여행에 참여중인지 확인
        if (!isMember){
            return res.status(403).send({
                message: "Forbidden"
            });
        }

        let chats=await models.chat.findAll({
            where:{
                TripId:trip.TripId
            },
            attributes:['UserId','Message','OrderId','Time','read']
        })

        let Unread=await models.chat.Unread(models,trip.TripId,UserId)//읽지 않은 메세지 수?
        if (Unread>0){
            await models.chat.readAll(models,trip.TripId,UserId)//읽음 처리?
        }
        return res.status(200).send({
            message: {status:true, reason: "방 조회 성공", chats}
        })
    },
    */
     //Get Unread chat(GET)
     async getUnreadChat(req, res) {
        const {TripId} = req.params;
        const {UserId} = req.token;
        let result = {};
        let chatList = raw2str(await models.Chat.findAll({
            where: {
                TripId: TripId
            }
        }));
        for (let i = 0; i < chatList.length; i++) {
            let readerList = await models.ChatRead.findAll({
                where: {
                    TripId: TripId,
                    ChatId: chatList[i].ChatId,
                    UserId: UserId
                }
            });
            if (readChat.length == 0) {
                let chat = {};
                chat.ChatId = chatList[i].ChatId;
                chat.UserId = chatList[i].UserId;
                chat.Content = chatList[i].Content;
                chat.CreatedAt = chatList[i].CreatedAt;
                await models.ChatRead.create({
                    TripId: TripId,
                    ChatId: chatList[i].ChatId,
                    UserId: UserId
                });
                result.push(chat);
            }
        }

    },
    //Send Chat(POST)
    async sendChat(req, res) {
        const {TripId} = req.params;
        const {UserId} = req.token;
        const {Content} = req.body;
        if (Content == undefined) {
            return res.send({status: false, reason: "Bad Request"});
        }
        let chat = raw2str(await models.Chat.create({
            TripId: TripId,
            UserId: UserId,
            Content: Content
        }));

        if (chat == null) {
            return res.send({status: false, reason: "채팅 전송 도중 오류가 발생했습니다."});
        }
        return res.send({status: true, reason: "채팅 전송 성공"});
    },
    //Delete Chat(DELETE)
    async deleteChat(req, res) {
        const {TripId} = req.params;
        const {ChatId} = req.body;
        const {UserId} = req.token;
        if (ChatId == undefined) {
            return res.send({status: false, reason: "Bad Request"});
        }
        let chat = await models.Chat.findByPk(ChatId);
        if (chat == null) {
            return res.status(404).send({status: false, reason: "Not Found"});
        }
        if (chat.UserId !== UserId) {
            return res.status(403).send({status: false, reason: "Forbidden"});
        }
        let result = await chat.destroy();
        return res.status(200).send({status: true, reason: "채팅 삭제 성공"});
    }
    
}
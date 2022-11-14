'use stricts';
const models = require('../../models');
const token=require('../../util/jwt');
const { raw2str } = require('../../util/rawtostr');

module.exports={
    async createChatRoom(req,res){
        if (req.params.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let chatroom=await models.ChatRoom.create({
            PlanId:req.params.PlanId,
            UserId:req.token.userId,
            RoomId:req.params.RoomId
        });
        if(chatroom===null){
            return res.status(404).send({
                message: "Not Found"
            });
        }
        return res.status(200).send({ status: true, reason: "채팅방 생성 성공", PlanId: chatroom.PlanId });
    },
    async getChatRoom(req,res){
        if (req.params.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let chatroom=await models.ChatRoom.findByPk(req.params.RoomId);
        if(chatroom===null){
            return res.status(404).send({
                message: "Not Found"
            });
        }
        return res.status(200).send({ status: true, reason: "채팅방 조회 성공", PlanId: chatroom.PlanId });
    },
    async deleteChatRoom(req,res){
        if (req.params.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let chatroom=await models.ChatRoom.destroy({
            where:{
                RoomId:req.params.RoomId
            }
        });
        if(chatroom===null){
            return res.status(404).send({
                message: "Not Found"
            });
        }
        return res.status(200).send({ status: true, reason: "채팅방 삭제 성공", PlanId: chatroom.PlanId });
    },
    async getChatRoomList(req,res){
        if (req.params.PlanId === undefined) {
            return res.status(400).send({
                message: "Bad Request"
            });
        }
        let chatroom=await models.ChatRoom.findAll({
            where:{
                PlanId:req.params.PlanId
            }
        });
        if(chatroom===null){
            return res.status(404).send({
                message: "Not Found"
            });
        }
        return res.status(200).send({ status: true, reason: "채팅방 리스트 조회 성공", PlanId: chatroom.PlanId });
    }
}
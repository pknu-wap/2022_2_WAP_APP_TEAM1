'use strict';
const models=require("../../models")
const {raw2str}=require("../../util/rawtostr")

module.exports={
    //create(PUT)
    async createRoom(req,res){
        const {TripId}=req.body
        const {UserId}=req.token

        if (TripId===undefined||UserId===undefined){
            return res.send({status:false, reason: "Bad Request"});
        }
        let transaction=await models.sequelize.transaction();
        try{    
            let Room=raw2str(await models.Chat.create({
                TripId:TripId,
                UserId:UserId
            }));
            if (Room==null){
                await transaction.rollback();
                return res.send({status:false, reason: "방 생성 도중 오류가 발생했습니다."});
            }
            await transaction.commit();
            return res.send({status:true, reason: "방 생성 성공", TripId:Room.TripId});
        }catch(err){
            await transaction.rollback();
            console.log('createRoom error : ',err);
            res.status(500).send({status:false, reason: "Internal Server Error"});
        }
    },
    //delete(DELETE)
    async deleteRoom(req,res){
        const {TripId}=req.body
        if (TripId==undefined){
            return res.send({status:false, reason: "Bad Request"});
        }
        let Room=await models.Chat.findByPk(TripId);
        if (Room==null){
            return res.status(404).send({
                message: "Not Found"
            });
        }
        let result=await Room.destroy();
        return res.status(200).send({status:true, reason: "방 삭제 성공"});
    },
    async createchat(req,res){
        const {UserId}=req.token
        const {TripId,Message}=req.body
        if (TripId==undefined||Message==undefined){
            return res.send({status:false, reason: "Bad Request"});
        }
        return res.send({status:true, reason: "메세지 저장 성공", Message:Message});
    },
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
    }
    
}
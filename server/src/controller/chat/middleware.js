const models =require("../../models");
const { raw2str } = require("../../util/rawtostr");

async function chatMiddleware(req,res,next){
    const {UserId}=req.token;
    const {TripId}=req.params;
    let chat=raw2str(await models.Chat.findByPk(TripId));
    if(chat==null){
        return res.status(404).send({status:false,reason:"Not Found"});
    }
    if (!await models.Chat.isMemberOf(models,TripId,UserId)){
        return res.status(403).send({status:false,reason:"Forbidden"});
    }
    req.chat=chat;
    next();
}
module.exports={chatMiddleware}
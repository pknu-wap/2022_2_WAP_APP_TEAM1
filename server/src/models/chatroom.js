'use strict';

const {raw2str}=require('../util/rawtostr');
module.exports=function(sequelize,DataTypes){
    const ChatRoom=sequelize.define('ChatRoom',{
        PlanId:
        {
            field:'PLAN_ID',
            type:DataTypes.STRING(32),
            primaryKey:true,
            allowNull:false,
            defaultValue:''
        },
        UserId:
        {
            field:'USER_ID',
            type:DataTypes.STRING(32),
            primaryKey:true,
            allowNull:false,
            defaultValue:''
        },
        RoomId:
        {
            field:'ROOM_ID',
            type:DataTypes.STRING(32),
            primaryKey:true,
            allowNull:false,
            defaultValue:''
        }
    },{
        underscored:true,
        freezeTableName:true,
        tableName:'DB_CHATROOM',
        hooks:{
            afterQuery:(result,options)=>{
                return raw2str(result);
            }
        }
    });
    ChatRoom.associate=function(models){
        ChatRoom.belongsTo(models.Plan,{
            foreignKey:'PlanId',
            targetKey:'PlanId'
        });
        ChatRoom.belongsTo(models.User,{
            foreignKey:'UserId',
            targetKey:'UserId'
        });
    };
    return ChatRoom;
};
'use strict';

const {raw2str}=require('../util/rawtostr');
module.exports=function(sequelize,DataTypes){
    const Chat=sequelize.define('Chat',{
        RoomId:
        {
            field:'ROOM_ID',
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
        Message:
        {
            field:'CONTENT',
            type:DataTypes.STRING(100),
            allowNull:false,
            defaultValue:''
        }
    },{
        underscored:true,
        freezeTableName:true,
        tableName:'DB_CHAT',
        hooks:{
            afterQuery:(result,options)=>{
                return raw2str(result);
            }
        }
    });
    Chat.associate=function(models){
        Chat.belongsTo(models.ChatRoom,{
            foreignKey:'RoomId',
            targetKey:'RoomId'
        });
        Chat.belongsTo(models.User,{
            foreignKey:'UserId',
            targetKey:'UserId'
        });
    };
    return Chat;
};
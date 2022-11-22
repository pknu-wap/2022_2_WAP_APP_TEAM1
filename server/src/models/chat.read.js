'use stricts';

module.exports=function(sequelize, DataTypes){
    const ChatRead=sequelize.define('ChatRead',{
        TripId:
        {
            field:'TRIP_ID',
            type:DataTypes.NUMBER,
            primaryKey:true,
            allowNull:false,
            defaultValue:''
        },
        ChatId:
        {
            field:'CHAT_ID',
            type:DataTypes.NUMBER,
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
        }
    });
    ChatRead.associate=function(models){
        models.ChatRead.belongsTo(models.Chat,{foreignKey:'TripId',targetKey:'TripId'});
        models.ChatRead.belongsTo(models.Chat,{foreignKey:'ChatId',targetKey:'ChatId'});
        models.ChatRead.belongsTo(models.User,{foreignKey:'UserId',targetKey:'UserId'});
    };
    return ChatRead;
}
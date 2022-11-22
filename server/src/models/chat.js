'use stricts';

module.exports=function(sequelize, DataTypes){
    const Chat=sequelize.define('Chat',{
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
            primaryKey:false,
            allowNull:false,
            defaultValue:''
        },
        Content:
        {
            field:'CONTENT',
            type:DataTypes.STRING(2000),
            primaryKey:false,
            allowNull:false,
            defaultValue:''
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_CHAT',
        timestamps: true

    });
    Chat.associate=function(models){
        models.Chat.belongsTo(models.Trip,{foreignKey:'TripId',targetKey:'TripId'});
        models.Chat.belongsTo(models.User,{foreignKey:'UserId',targetKey:'UserId'});
        models.Chat.hasMany(models.ChatRead,{foreignKey:'TripId',sourceKey:'TripId'});
        models.Chat.hasMany(models.ChatRead,{foreignKey:'ChatId',sourceKey:'ChatId'});
    };
    return Chat;
}
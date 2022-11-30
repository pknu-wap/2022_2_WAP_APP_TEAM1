'use stricts';

module.exports=function(sequelize, DataTypes){
    const ChatRead=sequelize.define('ChatRead',{
        TripId:
        {
            field:'TRIP_ID',
            type:DataTypes.INTEGER,
            primaryKey:true,
            allowNull:false,
            defaultValue:''
        },
        ChatId:
        {
            field:'CHAT_ID',
            type:DataTypes.INTEGER,
            primaryKey:true,
            allowNull:false,
            defaultValue:1
        },
        UserId:
        {
            field:'USER_ID',
            type:DataTypes.STRING(32),
            primaryKey:true,
            allowNull:false,
            defaultValue:''
        }
    },{
    underscored: true,
        freezeTableName: true,
        tableName: 'DB_CHAT_READ',
        timestamps: true,
        underscored: true
    });
    ChatRead.associate=function(models){
        models.ChatRead.belongsTo(models.Chat,{foreignKey:'TripId',targetKey:'TripId'});
        models.ChatRead.belongsTo(models.Chat,{foreignKey:'ChatId',targetKey:'ChatId'});
        models.ChatRead.belongsTo(models.User,{foreignKey:'UserId',targetKey:'UserId'});
    };
    return ChatRead;
}
'use stricts';
const { raw2str } = require('../util/rawtostr');

module.exports=function(sequelize, DataTypes){
    const Chat=sequelize.define('Chat',{
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
        timestamps: true,
        underscored: true,
        hooks: {
            beforeCreate: async (chat, options) => {
                const result = await sequelize.query('SELECT SEQ_CHAT.NEXTVAL AS CHAT_ID FROM DUAL', {
                    type: sequelize.QueryTypes.SELECT
                });
                Chat.ChatId = raw2str(result)[0].CHAT_ID;
            }
        }

    });
    Chat.associate=function(models){
        models.Chat.belongsTo(models.Trip,{foreignKey:'TripId',targetKey:'TripId'});
        models.Chat.belongsTo(models.User,{foreignKey:'UserId',targetKey:'UserId'});
    };
    return Chat;
}
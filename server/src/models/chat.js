'use strict';
const { raw2str } = require('../util/rawtostr');

module.exports=function(sequelize, DataTypes){
    const Chat=sequelize.define('Chat',{
        TripId:
            {
                field:'TRIP_ID',
                type:DataTypes.INTEGER,
                primaryKey:true,
                allowNull:false,
            },
        ChatId:
            {
                field:'CHAT_ID',
                type:DataTypes.INTEGER,
                primaryKey:true,
                allowNull:false,
                defaultValue: 0
            },
        UserId:
            {
                field:'USER_ID',
                type:DataTypes.STRING(32),
                allowNull:false,
            },
        Content:
            {
                field:'CONTENT',
                type:DataTypes.STRING(2000),
                allowNull:false,
            },
        Type:
            {
                field:'TYPE',
                type:DataTypes.INTEGER,
                allowNull:false,
            }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_CHAT',
        timestamps: true,
        hooks: {
            beforeCreate: async (Chat, options) => {
                const result = await sequelize.query(`SELECT SEQ_CHAT_${Chat.TripId}.NEXTVAL AS CHAT_ID FROM DUAL`, {
                    type: sequelize.QueryTypes.SELECT
                });
                Chat.ChatId = result[0].CHAT_ID;
            },
            afterFind: async (Chat, options) => {
                Chat = raw2str(Chat);
            }
        }

    });
    Chat.associate=function(models){
        Chat.belongsTo(models.Trip,{foreignKey:'TripId',targetKey:'TripId'});
        Chat.belongsTo(models.User,{foreignKey:'UserId',targetKey:'UserId'});
    };
    return Chat;
};
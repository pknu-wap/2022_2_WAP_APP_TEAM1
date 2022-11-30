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
            defaultValue:''
        },
        UserId:
        {
            field:'USER_ID',
            type:DataTypes.STRING(32),
            allowNull:false,
            defaultValue:''
        },
        Content:
        {
            field:'CONTENT',
            type:DataTypes.STRING(2000),
            allowNull:false,
            defaultValue:''
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_CHAT',
        timestamps: true,
        hooks: {
            beforeCreate: async (Chat, options) => {
                const result = await sequelize.query(`SELECT SEQ_CHAT_${Chat.TripId}.NEXTVAL
                                                      AS CHAT_ID 
                                                      FROM DUAL`, {
                                                        type: sequelize.QueryTypes.SELECT,
                                                        transaction: options.transaction
                                                    });
                Chat.ChatId = result[0].CHAT_ID;
                console.log(result[0].CHAT_ID);
            }
        }

    });
    Chat.associate=function(models){
        Chat.belongsTo(models.Trip,{foreignKey:'TripId',targetKey:'TripId'});
        Chat.belongsTo(models.User,{foreignKey:'UserId',targetKey:'UserId'});
    };
    return Chat;
}
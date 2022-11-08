const { raw2str } = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const User = sequelize.define('User', {
        UserId: {
            field: 'USER_ID',
            type: DataTypes.STRING(32),
            primaryKey: true,
            allowNull: false,
            defaultValue: ''
        },
        AccountType: {
            field: 'ACCOUNTTYPE',
            type: DataTypes.INTEGER,
            allowNull: false
        },
        Username: {
            field: 'USERNAME',
            type: DataTypes.STRING(100),
            allowNull: true
        },
        Password: {
            field: 'PASSWORD',
            type: DataTypes.STRING(100),
            allowNull: true
        },
        PhoneNum: {
            field: 'PHONENUM',
            type: DataTypes.STRING(20),
            allowNull: true
        },
        Nickname: {
            field: 'NICKNAME',
            type: DataTypes.STRING(40),
            allowNull: true
        },
        ProfileImage: {
            field: 'PROFILEIMAGE',
            type: DataTypes.STRING(120),
            allowNull: true
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_USER',
        hooks: {
            beforeCreate: async (user, options) => {
                let result = await sequelize.query('SELECT SYS_GUID() AS USER_ID FROM DUAL', {
                    type: sequelize.QueryTypes.SELECT
                });
                user.UserId = raw2str(result)[0].USER_ID;
            },
            afterQuery: (result, options) => {
                return raw2str(result);
            }
        }
    });
    return User;
}
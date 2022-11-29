const {raw2str} = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const FileInfo = sequelize.define('FileInfo', {
        FileName:
            {
                field: 'FILENAME',
                type: DataTypes.STRING(120),
                primaryKey: true,
                allowNull: false
            },
        SHA256:
            {
                field: 'SHA256',
                type: DataTypes.STRING(64),
                allowNull: false
            },
        MD5:
            {
                field: 'MD5',
                type: DataTypes.STRING(32),
                allowNull: false
            },
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_FILE_INFO'
    });
    FileInfo.associate = function (models) {
        FileInfo.hasMany(models.User, {foreignKey: 'ProfileImage', sourceKey: 'FileName'});
    };
    return FileInfo;
};
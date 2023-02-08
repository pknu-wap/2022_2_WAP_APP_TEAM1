const {raw2str} = require('../util/rawtostr');
module.exports = (sequelize, DataTypes) => {
    const TripParticipant = sequelize.define('TripParticipant', {
        TripId: {
            field: 'TRIP_ID',
            type: DataTypes.INTEGER,
            allowNull: false,
            primaryKey: true
        },
        UserId: {
            field: 'USER_ID',
            type: DataTypes.STRING(32),
            allowNull: false,
            primaryKey: true
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'TRIP_PARTICIPANT'
    });
    TripParticipant.associate = function (models) {
        TripParticipant.belongsTo(models.Trip, {foreignKey: 'TripId', targetKey: 'TripId'});
        TripParticipant.belongsTo(models.User, {foreignKey: 'UserId', targetKey: 'UserId'});
    };
    return TripParticipant;
};
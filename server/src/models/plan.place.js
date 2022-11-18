'use strict';
const { raw2str } = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const PlanPlace = sequelize.define('PlanPlace', {
        TripId:
        {
            field: 'TRIP_ID',
            type: DataTypes.INTEGER,
            primaryKey: true,
            allowNull: false,
        },
        PlanId:
        {
            field: 'PLAN_ID',
            type: DataTypes.INTEGER,
            primaryKey: true,
            allowNull: false,
        },
        PlaceId:
        {
            field: 'PLACE_ID',
            type: DataTypes.STRING(32),
            allowNull: false
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'PLAN_PLACE',
        timestamps: true
    });

    PlanPlace.associate = function (models) {
        PlanPlace.belongsTo(models.Plan, { foreignKey: 'TripId', targetKey: 'TripId' });
        PlanPlace.belongsTo(models.Plan, { foreignKey: 'PlanId', targetKey: 'PlanId' });
        PlanPlace.belongsTo(models.Place, { foreignKey: 'PlaceId', targetKey: 'PlaceId' });
    };
    return PlanPlace;
}
'use strict';
const { raw2str } = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const PlanPlace = sequelize.define('PlanPlace', {
        PlanDetailId:
        {
            field: 'PLAN_DETAIL_ID',
            type: DataTypes.STRING(32),
            primaryKey: true,
            allowNull: false,
            defaultValue: ''
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
        //PlanPlace.hasOne(models.PlanDetail, { foreignKey: 'PlanDetailId', targetKey: 'PlanDetailId' });
        //PlanPlace.belongsTo(models.Place, { foreignKey: 'PlaceId', targetKey: 'PlaceId' });
    };
    return PlanPlace;
}
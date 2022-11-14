'use strict';
const { raw2str } = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const PlanMemo = sequelize.define('PlanMemo', {
        TripId:
        {
            field: 'TRIP_ID',
            type: DataTypes.INTEGER,
            allowNull: false,
            primaryKey: true
        },
        PlanId:
        {
            field: 'PLAN_ID',
            type: DataTypes.INTEGER,
            allowNull: false,
            primaryKey: true
        },
        Content:
        {
            field: 'CONTENT',
            type: DataTypes.STRING(2000),
            allowNull: true
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'PLAN_MEMO',
        timestamps: true
    });

    PlanMemo.associate = function (models) {
        PlanMemo.belongsTo(models.Plan, { foreignKey: 'PlanId', targetKey: 'PlanId' });
        PlanMemo.belongsTo(models.Plan, { foreignKey: 'TripId', targetKey: 'TripId' });
    };
    return PlanMemo;
};
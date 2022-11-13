'use strict';
const { raw2str } = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const PlanMemo = sequelize.define('PlanMemo', {
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
            allowNull: true
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
        PlanMemo.belongsTo(models.PlanDetail, { foreignKey: 'PlanDetailId', targetKey: 'PlanDetailId' });
        PlanMemo.belongsTo(models.PlanPlace, { foreignKey: 'PlaceId', targetKey: 'PlaceId' });
    };
    return PlanMemo;
};
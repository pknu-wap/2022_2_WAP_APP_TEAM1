'use strict';
const { raw2str } = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const PlanDetail = sequelize.define('PlanDetail', {
        PlanDetailId:
        {
            field: 'PLAN_DETAIL_ID',
            type: DataTypes.STRING(32),
            primaryKey: true,
            allowNull: false,
            defaultValue: ''
        },
        PlanId:
        {
            field: 'PLAN_ID',
            type: DataTypes.STRING(32),
            allowNull: false
        },
        OrderIndex:
        {
            field: 'ORDER_INDEX',
            type: DataTypes.INTEGER,
            allowNull: false,
            defaultValue: 0
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_PLAN_DETAIL',
        hooks: {
            beforeCreate: async (plan_detail, options) => {
                let result = await sequelize.query('SELECT MAX(ORDER_INDEX) AS MAX_ORDER_INDEX FROM DB_PLAN_DETAIL WHERE PLAN_ID = :PLAN_ID', {
                    replacements: { PLAN_ID: plan_detail.PlanId },
                    type: sequelize.QueryTypes.SELECT
                });
                plan_detail.OrderIndex = result[0].MAX_ORDER_INDEX + 1;
                result = await sequelize.query('SELECT SYS_GUID() AS PLAN_DETAIL_ID FROM DUAL', {
                    type: sequelize.QueryTypes.SELECT
                });
                plan_detail.PlanDetailId = raw2str(result)[0].PLAN_DETAIL_ID;
            }
        }
    });

    PlanDetail.associate = function (models) {
        PlanDetail.belongsTo(models.Plan, { foreignKey: 'PlanId', targetKey: 'PlanId' });
        PlanDetail.hasMany(models.PlanMemo, { foreignKey: 'PlanDetailId', sourceKey: 'PlanDetailId' });
    }; 
    return PlanDetail;
};
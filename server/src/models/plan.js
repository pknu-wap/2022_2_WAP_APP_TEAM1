'use strict';
const {raw2str} = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const Plan = sequelize.define('Plan', {
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
                primaryKey: true,
                allowNull: false,
                defaultValue: 0
            },
        Day:
            {
                field: 'DAY',
                type: DataTypes.INTEGER,
                allowNull: false
            },
        Type:
            {
                field: 'TYPE',
                type: DataTypes.INTEGER,
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
        tableName: 'DB_PLAN',
        hooks: {
            beforeCreate: async (plan, options) => {

                let [result, metadata] = await sequelize.query(`SELECT MAX(ORDER_INDEX) AS MAX_ORDER_INDEX
                                                                FROM DB_PLAN
                                                                WHERE TRIP_ID = ${plan.TripId}`);
                plan.OrderIndex = result[0].MAX_ORDER_INDEX + 1;
                [result, metadata] = await sequelize.query(`SELECT SEQ_PLAN_${plan.TripId}.NEXTVAL AS PLAN_ID
                                                            FROM DUAL`);
                plan.PlanId = result[0].PLAN_ID;
            }
        }
    });

    Plan.associate = function (models) {
        Plan.belongsTo(models.Trip, {foreignKey: 'TripId', targetKey: 'TripId'});
        Plan.hasMany(models.PlanMemo, {foreignKey: 'PlanId', sourceKey: 'PlanId'});
        Plan.hasMany(models.PlanMemo, {foreignKey: 'TripId', sourceKey: 'TripId'});
        Plan.hasMany(models.PlanPlace, {foreignKey: 'PlanId', sourceKey: 'PlanId'});
        Plan.hasMany(models.PlanPlace, {foreignKey: 'TripId', sourceKey: 'TripId'});
        Plan.hasMany(models.PlanFlight, {foreignKey: 'PlanId', sourceKey: 'PlanId'});
        Plan.hasMany(models.PlanFlight, {foreignKey: 'TripId', sourceKey: 'TripId'});
    };
    return Plan;
};
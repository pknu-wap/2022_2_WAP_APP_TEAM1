const {raw2str} = require('../util/rawtostr');
module.exports = (sequelize, DataTypes) => {
    const PlanParticipant = sequelize.define('PlanParticipant', {
        PlanId: {
            field: 'PLAN_ID',
            type: DataTypes.STRING(32),
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
        tableName: 'PLAN_PARTICIPANT',
        hooks: {
            afterQuery: (result, options) => {
                return raw2str(result);
            }
        }
    });
    PlanParticipant.associate = function (models) {
        PlanParticipant.belongsTo(models.Plan, {
            foreignKey: 'PlanId',
            targetKey: 'PlanId'
        });
        PlanParticipant.belongsTo(models.User, {
            foreignKey: 'UserId',
            targetKey: 'UserId'
        });
    };
    return PlanParticipant;
};
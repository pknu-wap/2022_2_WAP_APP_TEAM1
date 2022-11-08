const {raw2str} = require('../util/rawtostr');
module.exports = (sequelize, DataTypes) => {
    const Plan = sequelize.define('Plan', {
        PlanId: {
            field: 'PLAN_ID',
            type: DataTypes.STRING(32),
            primaryKey: true,
            allowNull: false,
            defaultValue: '',
        },
        OwnerId: {
            field: 'OWNER_ID',
            type: DataTypes.STRING(32),
            allowNull: false,
        },
        StartDate: {
            field: 'START_DATE',
            type: DataTypes.DATE,
            allowNull: false
        },
        EndDate: {
            field: 'END_DATE',
            type: DataTypes.DATE,
            allowNull: false
        },
        Name: {
            field: 'NAME',
            type: DataTypes.STRING(20),
            allowNull: false
        },
        Region: {
            field: 'REGION',
            type: DataTypes.STRING(20),
            allowNull: false
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_PLAN',
        hooks: {
            beforeCreate: async (plan, options) => {
                const result = await sequelize.query('SELECT SYS_GUID() AS PLAN_ID FROM DUAL', {
                    type: sequelize.QueryTypes.SELECT
                });
                plan.PlanId = raw2str(result)[0].PLAN_ID;
            }
        }
    });
    Plan.isMemberOf = async function(models, planId, userId) {
        Plan.findOne({
            where: {
                PlanId: planId
            },
            include: [{
                model: models.PlanParticipant
            }]
        }).then(plan => {
            if (plan) {
                let isMember = plan.OwnerId === userId;
                plan.PlanParticipants.forEach(participant => {
                    if (participant.UserId === userId) {
                        isMember = true;
                    }
                });
                return isMember;
            } else {
                return false;
            }
        });
    };
    Plan.associate = function (models) {
        Plan.hasMany(models.PlanParticipant, { foreignKey: 'PlanId', sourceKey: 'PlanId' });
        Plan.hasMany(models.PlanDetail, { foreignKey: 'PlanId', sourceKey: 'PlanId' });
        Plan.belongsTo(models.User, { foreignKey: 'OwnerId', targetKey: 'UserId' });
    };
    return Plan;
}
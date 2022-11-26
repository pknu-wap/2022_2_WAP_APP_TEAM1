const {raw2str} = require('../util/rawtostr');
module.exports = (sequelize, DataTypes) => {
    const Trip = sequelize.define('Trip', {
        TripId: {
            field: 'TRIP_ID',
            type: DataTypes.INTEGER,
            primaryKey: true,
            allowNull: false,
            defaultValue: 0,
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
            type: DataTypes.STRING(200),
            allowNull: false
        },
        Region: {
            field: 'REGION',
            type: DataTypes.STRING(200),
            allowNull: false
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_TRIP',
        hooks: {
            beforeDestroy: async (trip, options) => {
                await sequelize.query(`DROP SEQUENCE SEQ_CHAT_${trip.TripId}`);
                await sequelize.query(`DROP SEQUENCE SEQ_PLAN_${trip.TripId}`);
            },
            beforeCreate: async (trip, options) => {
                const result = await sequelize.query('SELECT SEQ_TRIP.NEXTVAL AS TRIP_ID FROM DUAL', {
                    type: sequelize.QueryTypes.SELECT
                });
                trip.TripId = raw2str(result)[0].TRIP_ID;
            },
            afterCreate: async (trip, options) => {
                await sequelize.query(`CREATE SEQUENCE SEQ_CHAT_${trip.TripId} INCREMENT BY 1 START WITH 1 MAXVALUE 9999999 MINVALUE 1 CACHE 20 ORDER;`);
                await sequelize.query(`CREATE SEQUENCE SEQ_PLAN_${trip.TripId} INCREMENT BY 1 START WITH 1 MAXVALUE 9999999 MINVALUE 1 CACHE 20 ORDER;`);
                }
        }
    });

    Trip.isMemberOf = async function(models, tripId, userId) {
        let trip = raw2str(await Trip.findByPk(tripId));
        if (trip == null) { return false; }
        if (trip.OwnerId === userId) {
            return true;
        }
        let tripParticipant = raw2str(await models.TripParticipant.findOne({
            where: {
                TripId: tripId,
                UserId: userId
            }
        }));
        return tripParticipant != null;   
    };
    
    Trip.associate = function (models) {
        Trip.hasMany(models.TripParticipant, {foreignKey: 'TripId', sourceKey: 'TripId'});
        Trip.belongsTo(models.User, {foreignKey: 'OwnerId', targetKey: 'UserId'});
    };
    return Trip;
}
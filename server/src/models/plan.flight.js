'use strict';

module.exports = function (sequelize, DataTypes) {
    const PlanFlight = sequelize.define('PlanFlight', {
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
        AirlineCode:
            {
                field: 'AIRLINE_CODE',
                type: DataTypes.STRING(20),
            },
        FlightNum:
            {
                field: 'FLIGHT_NUM',
                type: DataTypes.STRING(20),
            },
        DepartureTime:
            {
                field: 'DEPARTURE_TIME',
                type: DataTypes.DATE,
            },
        ArrivalTime:
            {
                field: 'ARRIVAL_TIME',
                type: DataTypes.DATE,
            },
        DepartureAirport:
            {
                field: 'DEPARTURE_AIRPORT',
                type: DataTypes.STRING(20),
            },
        ArrivalAirport:
            {
                field: 'ARRIVAL_AIRPORT',
                type: DataTypes.STRING(20),
            }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'PLAN_FLIGHT',
    });

    PlanFlight.associate = function (models) {
        PlanFlight.belongsTo(models.Plan, {foreignKey: 'PlanId', targetKey: 'PlanId'});
        PlanFlight.belongsTo(models.Plan, {foreignKey: 'TripId', targetKey: 'TripId'});
    }
    return PlanFlight;
}
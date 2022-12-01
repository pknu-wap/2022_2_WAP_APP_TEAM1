'use strict';
const {raw2str} = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const Place = sequelize.define('Place', {
        PlaceId:
            {
                field: 'PLACE_ID',
                type: DataTypes.STRING(200),
                primaryKey: true,
                allowNull: false,
                defaultValue: ''
            },
        Latitude:
            {
                field: 'LATITUDE',
                type: DataTypes.STRING(200),
                allowNull: false
            },
        Longitude:
            {
                field: 'LONGITUDE',
                type: DataTypes.STRING(200),
                allowNull: false
            },
        Category:
            {
                field: 'CATEGORY',
                type: DataTypes.STRING(200),
                allowNull: false
            },
        Name:
            {
                field: 'NAME',
                type: DataTypes.STRING(100),
                allowNull: false
            },
        RoadAddress:
            {
                field: 'ROAD_ADDRESS',
                type: DataTypes.STRING(200),
                allowNull: false
            }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_PLACE'
    });

    Place.associate = function (models) {
        Place.hasMany(models.PlanPlace, {foreignKey: 'PlaceId', sourceKey: 'PlaceId'});
    };
    return Place;
};
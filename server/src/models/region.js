'use strict';
const { raw2str } = require('../util/rawtostr');
module.exports = function (sequelize, DataTypes) {
    const Region = sequelize.define('Region', {
        AdministrationCode:
        {
            field: 'ADMINISTRATION_CODE',
            type: DataTypes.INTEGER,
            primaryKey: true,
            allowNull: false
        },
        CityName:
        {
            field: 'CITY_NAME',
            type: DataTypes.STRING(100),
            allowNull: false
        },
        DistrictName:
        {
            field: 'DISTRICT_NAME',
            type: DataTypes.STRING(100),
            allowNull: false
        },
        TownName:
        {
            field: 'TOWN_NAME',
            type: DataTypes.STRING(100),
            allowNull: false
        }
    }, {
        underscored: true,
        freezeTableName: true,
        tableName: 'DB_REGION',
    });

    Region.associate = function (models) {
        Region.hasMany(models.Place, { foreignKey: 'AdministrationCode', sourceKey: 'AdministrationCode' });
    }; 
    return Region;
};

const { Sequelize } = require('sequelize');


module.exports = {
  getDb: function () {
    return new Sequelize('xe', 'wetrip', 'wetrip', {
      database: 'xe',
      username: 'wetrip',
      password: 'wetrip',
      host: 'winocreative.synology.me',
      port: '49154',
      pool: {
        maxConnections: 5,
        maxIdleTime: 3000
      },
      dialect: 'oracle',
      logging: console.log,
      define: {
        timestamps: false
      },
      quoteIdentifiers: false
    });
  }
};
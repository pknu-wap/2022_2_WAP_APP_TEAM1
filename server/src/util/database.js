
const { Sequelize } = require('sequelize');


module.exports = new Sequelize({
      database: process.env.DB_SID,
      username: process.env.DB_USER,
      password: process.env.DB_PASS,
      host: process.env.DB_HOST,
      port: process.env.DB_PORT,
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
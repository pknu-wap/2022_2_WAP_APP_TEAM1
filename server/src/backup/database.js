
const { Sequelize } = require('sequelize');
const { raw2str } = require('./rawtostr');
const sequelize = new Sequelize({
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
  logQueryParameters: true,
  define: {
    timestamps: false
  },
  quoteIdentifiers: false
});

const generate_uuid = () => {
  return sequelize.query('SELECT SYS_GUID() AS GUID FROM DUAL', { type: Sequelize.QueryTypes.SELECT })
    .then((data) => {
      return raw2str(data)[0].GUID;
    })
    .catch((err) => {
      console.log('generate_uuid 도중 오류 발생: ', err);
      return null;
    });
};
module.exports = {sequelize, generate_uuid};
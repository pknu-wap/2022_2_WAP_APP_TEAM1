const dotenv = require("dotenv");
const oracle = require("oracledb");
const sequelize=require("sequelize");


dotenv.config();

const connection = oracle.createConnection({
  host     : process.env.DB_HOST,
  user     : process.env.DB_USER,
  password : process.env.DB_PASS,
  database : process.env.DB_SID,
  port     : process.env.DB_PORT
});

connection.connect();
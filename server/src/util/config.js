require("dotenv").config({ path: "../.env" });

const development = {
  database: process.env.DB_SID,
  username: process.env.DB_USER,
  password: process.env.DB_PASS,
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  dialect: "oracle",
  logging: console.log,
  logQueryParameters: true,
  define: {
    timestamps: false
  },
  quoteIdentifiers: false
};

const production = {
  database: process.env.DB_SID,
  username: process.env.DB_USER,
  password: process.env.DB_PASS,
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  dialect: "oracle",
  logging: console.log,
  logQueryParameters: true,
  define: {
    timestamps: false
  },
  quoteIdentifiers: false
};

const test = {
  database: process.env.DB_SID,
  username: process.env.DB_USER,
  password: process.env.DB_PASS,
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  dialect: "oracle",
  logging: console.log,
  logQueryParameters: true,
  define: {
    timestamps: false
  },
  quoteIdentifiers: false
};

module.exports = { development, production, test };
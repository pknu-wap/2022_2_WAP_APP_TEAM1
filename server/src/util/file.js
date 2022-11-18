'use strict';

const {QueryTypes} = require("sequelize");
const crypto = require('crypto');
const fs = require('fs');
const path = require('path');
const models = require('../models');
module.exports = async function (buffer, originalfilename) {
    let transaction = await models.sequelize.transaction();
    try {
        let sha256 = crypto.createHash("sha256").update(buffer).digest("hex");
        let md5 = crypto.createHash("md5").update(buffer).digest("hex");
        let filename = sha256 + md5 + path.extname(originalfilename);
        let fileinfo = await models.FileInfo.findOne({
            where: {
                SHA256: sha256,
                MD5: md5
            }
        });
        if (fileinfo === null) {
            fs.writeFileSync(path.join(__dirname, '../data' + filename), buffer);
            await models.FileInfo.create({
                FileName: filename,
                SHA256: sha256,
                MD5: md5
            }, {transaction: transaction});
            await transaction.commit();
            return {result: true, fileName: filename};
        }
        return {result: true, fileName: fileinfo.FileName};
    } catch (err) {
        await transaction.rollback();
        return {result: false, reason: err};
    }
}